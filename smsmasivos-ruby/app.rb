require 'sinatra'
require 'sinatra/json'
require 'sinatra/reloader' if development?
require 'httparty'
require 'date'

set :public_folder, File.dirname(__FILE__) + '/public'

helpers do
  def smsmasivos_api_post(endpoint, apikey, params, response_format = 'json')
    url = "https://api.smsmasivos.com.mx/#{endpoint}"
    headers = { 'apikey' => apikey }
    begin
      response = HTTParty.post(url, headers: headers, body: params)
      response.body
    rescue => e
      if response_format == 'json'
        return { success: false, message: "Error en la conexión: #{e.message}" }.to_json
      else
        return "<error>Error en la conexión: #{e.message}</error>"
      end
    end
  end
end

# --------- VISTAS PRINCIPALES ---------
get '/' do
  send_file File.join(settings.public_folder, 'index.html')
end

get '/2factorVerification' do
  send_file File.join(settings.public_folder, '2factorVerification.html')
end

# --------- ENDPOINTS API ---------

post '/send_sms' do
  data = JSON.parse(request.body.read)
  apikey = data['apikey']
  numbers = data['numbers']
  message = data['message']
  country_code = data['country_code']
  sandbox = data['sandbox'] || 0

  if [apikey, numbers, message, country_code].any?(&:nil?) || [apikey, numbers, message, country_code].any?(&:empty?)
    return json(success: false, message: 'Parámetros insuficientes.')
  end

  params = {
    message: message,
    numbers: numbers,
    country_code: country_code,
    name: "Campaña demo #{DateTime.now.strftime('%Y-%m-%d %H:%M:%S')}",
    sandbox: sandbox
  }
  response = smsmasivos_api_post('sms/send', apikey, params)
  content_type :json
  response
end

post '/register_2fa' do
  data = JSON.parse(request.body.read)
  apikey = data['apikey']
  company = data['company']
  phone_number = data['phone_number']
  country_code = data['country_code']
  code_length = data['code_length'] || 4
  format = (data['format'] || 'json').downcase

  if [apikey, company, phone_number, country_code].any?(&:nil?) || [apikey, company, phone_number, country_code].any?(&:empty?)
    return json(success: false, message: 'Parámetros insuficientes.')
  end

  params = {
    phone_number: phone_number,
    country_code: country_code,
    company: company,
    code_length: code_length,
    template: 'a',
    format: format
  }
  endpoint = "protected/#{format}/phones/verification/start"
  response = smsmasivos_api_post(endpoint, apikey, params, format)
  if format == 'json'
    content_type :json
    response
  else
    content_type 'text/xml'
    response
  end
end

post '/validate_2fa' do
  data = JSON.parse(request.body.read)
  apikey = data['apikey']
  phone_number = data['phone_number']
  verification_code = data['verification_code']
  format = (data['format'] || 'json').downcase

  if [apikey, phone_number, verification_code].any?(&:nil?) || [apikey, phone_number, verification_code].any?(&:empty?)
    return json(success: false, message: 'Parámetros insuficientes.')
  end

  params = {
    phone_number: phone_number,
    verification_code: verification_code,
    format: format
  }
  endpoint = "protected/#{format}/phones/verification/check"
  response = smsmasivos_api_post(endpoint, apikey, params, format)
  if format == 'json'
    content_type :json
    response
  else
    content_type 'text/xml'
    response
  end
end
