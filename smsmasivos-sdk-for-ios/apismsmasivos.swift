//
//  apismsmasivos.swift
//  apismsmasivos
//
//  Created by Josue Rosales on 28-05-19.
//  Copyright © 2019 Vzert. All rights reserved.
//

import Foundation

/**
 # Smsmasivos API
 SDK para la inteaccion de swift con la API de smsmasivos.
 */
public class apismsmasivos {
    
    var urlComponents = URLComponents()
    
    var lang = "es"
    var token = ""
    var apikey = ""
    
    public init(apikey: String) {
        urlComponents.scheme = "https"
        urlComponents.host = "api.smsmasivos.com.mx"
        self.apikey = apikey
    }
    
    struct Credits: Codable {
        let lang: String
    }
    
    struct Sms: Codable {
        let message: String
        let numbers: String
        let country_code: Int
        let name: String
        let lang: String
        let sandbox: Bool
    }
    
    struct Contact: Codable {
        let list_key: String
        let number: String
        let name: String
        let email: String
        let lang: String
    }
    
    struct Report: Codable {
        let start_date: String
        let end_date: String
        let lang: String
        let sandbox: Bool
    }
    
    struct Registry: Codable {
        let phone_number: String
        let country_code: Int
        let code_length: Int
        let expiration_date: String
        let locale: String
        let company: String
    }
    
    struct Validate: Codable {
        let phone_number: String
        let verification_code: String
        let locale: String
    }
    
    /**
     ## Agregar Contacto
     Agrega información de tus contactos, como: Nombre, Número y/o Correo electrónico a cualquier agenda/canal que tengas configurado en el panel.
     
     - Parameters:
        - list_key: Clave hash de la agenda a la que deseas agregar el contacto.
        - number: Numero del contacto a registrar.
        - name: Nombre del contacto a registrar.
        - email: Cuenta de correo eletrónico del contacto a registrar.
        - lang: Idioma de la respuesta. Por defecto 'es'.
        - completion: Funcion de finalizacion, devolvuelve la respuesta del servidor y un informe de error.
            * Data: Respuesta del servidor contenida en un objeto NSDictionary
            * Error: Objeto Error con informacion del problema.
    */
    public func addContact(list_key:String, number:String, name:String = "", email:String = "", lang:String = "es", completion:((NSDictionary?, Error?) -> Void)?){
        let path = "/contacts/add/"
        let encoder = JSONEncoder()
        do {
            let jsonData = try encoder.encode(Contact(list_key: list_key, number: number, name: name, email: email, lang: lang))
            precessRequest(path, jsonData) { (data, error) in
                completion?(data, error)
            }
        } catch {
            completion?(nil, error)
        }
    }
    
    /**
     ## Enviar SMS
     Para enviar SMS a través de nuestra API.
     
     - Parameters:
        - message: Mensaje que desea enviar. Si el mensaje contiene algún error o sobrepasa el número de caracteres permitidos se le indicará en la respuesta.
        - numbers: Lista de números a los que desea enviar el mensaje. Los números deben ir separados por una coma (,).
        - country_code: Código de país al que desea enviar el mensaje. Consulta nuestra lista de países en las que tenemos disponible nuestro servicio de envío de SMS.
        - name: Nombre para identificar los envíos realizados en Reportes.
        - lang: Idioma de la respuesta. Por defecto 'es'.
        - sandbox: Para enviar SMS en modo de pruebas indica este atributo con true. En caso contrario el mensaje será enviado y descontado de tu crédito. Por defecto false.
        - completion: Funcion de finalizacion, devolvuelve la respuesta del servidor y un informe de error.
            * Data: Respuesta del servidor contenida en un objeto NSDictionary
            * Error: Objeto Error con informacion del problema.
    */
    public func sendSMS(message:String, numbers:String, country_code:Int, name:String = "", lang:String = "es", sandbox:Bool = true, completion:((NSDictionary?, Error?) -> Void)?){
        let path = "/sms/send/"
        let encoder = JSONEncoder()
        do {
            let jsonData = try encoder.encode(Sms(message: message, numbers: numbers, country_code: country_code, name: name, lang: self.lang, sandbox: sandbox))
            precessRequest(path, jsonData) { (data, error) in
                completion?(data, error)
            }
        } catch {
            completion?(nil, error)
        }
    }
    
    /**
     ## Reportes
     Obtener un reporte detallado de tus envíos para ver el estatus de tus mensajes enviados indicando un rango de fechas.
     
     - Parameters:
        - start_date: Fecha de inicio para filtrar el reporte de los mensajes enviados entre el rango de fechas definida.
        - end_date: Fecha de fin para filtrar el reporte de los mensajes enviados entre el rango de fechas definida.
        - sandbox: Para enviar SMS en modo de pruebas indica este atributo con true. En caso contrario el mensaje será enviado y descontado de tu crédito. Por defecto false.
        - lang: Idioma de la respuesta. Por defecto 'es'.
        - completion: Funcion de finalizacion, devolvuelve la respuesta del servidor y un informe de error.
            * Data: Respuesta del servidor contenida en un objeto NSDictionary
            * Error: Objeto Error con informacion del problema.
     */
    public func getReports(start_date:Date, end_date:Date, lang:String = "es", sandbox:Bool = true, completion:((NSDictionary?, Error?) -> Void)?){
        let path = "/reports/generate/"
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
        let encoder = JSONEncoder()
        do {
            let jsonData = try encoder.encode(Report(start_date: dateFormatter.string(from: start_date), end_date: dateFormatter.string(from: end_date), lang: self.lang, sandbox: sandbox))
            precessRequest(path, jsonData) { (data, error) in
                completion?(data, error)
            }
        } catch {
            completion?(nil, error)
        }
    }
    
    /**
     ## Créditos
     Obtener la cantidad de créditos que tienes disponibles.
     
     - Parameters:
        - lang: Idioma de la respuesta. Por defecto 'es'.
        - completion: Funcion de finalizacion, devolvuelve la respuesta del servidor y un informe de error.
            * Data: Respuesta del servidor contenida en un objeto NSDictionary
            * Error: Objeto Error con informacion del problema.
     */
    public func getCredits(lang:String = "es", completion:((NSDictionary?, Error?) -> Void)?){
        let path = "/credits/consult/"
        let encoder = JSONEncoder()
        do {
            let jsonData = try encoder.encode(Credits(lang: lang))
            precessRequest(path, jsonData) { (data, error) in
                completion?(data, error)
            }
        } catch {
            completion?(nil, error)
        }
    }
    
    /**
     ## 2FA (autenticación de doble factor) Registro
     Envío de PINs y contraseñas mediante SMS.
     
     - Parameters:
        - phone_number: Número de telefono a verificar.
        - country_code: Código de país al que desea enviar el mensaje. Consulta nuestra lista de países en las que tenemos disponible nuestro servicio de envío de SMS.
        - code_length: Longitud del código de verificación. Por defecto 4.
        - expiration_date: Fecha de expiración para el código de verificación.
        - lang: Idioma de la respuesta. Por defecto 'es'.
        - company: Nombre de la compañía mostrada al usuario en el mensaje de verificación.
        - completion: Funcion de finalizacion, devolvuelve la respuesta del servidor y un informe de error.
            * Data: Respuesta del servidor contenida en un objeto NSDictionary
            * Error: Objeto Error con informacion del problema.
     */
    public func registryDoubleFactor(phone_number:String, country_code:Int, code_length:Int = 4, expiration_date:Date = Date(), lang:String = "es", company:String = "", completion:((NSDictionary?, Error?) -> Void)?){
        let path = "/protected/json/phones/verification/start/"
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
        let encoder = JSONEncoder()
        do {
            let jsonData = try encoder.encode(Registry(phone_number: phone_number, country_code: country_code, code_length: code_length, expiration_date: dateFormatter.string(from: expiration_date), locale: self.lang, company: company))
            precessRequest(path, jsonData) { (data, error) in
                completion?(data, error)
            }
        } catch {
            completion?(nil, error)
        }
    }
    
    /**
     ## 2FA (autenticación de doble factor) Validación
     Validacion del PIN y/o contraseñas enviado mediante SMS.
     
     - Parameters:
        - phone_number: Número de telefono a verificar.
        - verification_code: El código enviado al usuario mediante el metodo `registryDoubleFactor`.
        - lang: Idioma de la respuesta. Por defecto 'es'.
        - completion: Funcion de finalizacion, devolvuelve la respuesta del servidor y un informe de error.
            * Data: Respuesta del servidor contenida en un objeto NSDictionary
            * Error: Objeto Error con informacion del problema.
     */
    public func validationDoubleFactor(phone_number:String, verification_code:String, lang:String = "es", completion:((NSDictionary?, Error?) -> Void)?){
        let path = "/protected/json/phones/verification/check/"
        let encoder = JSONEncoder()
        do {
            let jsonData = try encoder.encode(Validate(phone_number: phone_number, verification_code: verification_code, locale: self.lang))
            precessRequest(path, jsonData) { (data, error) in
                completion?(data, error)
            }
        } catch {
            completion?(nil, error)
        }
    }
    
    func precessRequest(_ path:String, _ post:Data, completion:((NSDictionary?, Error?) -> Void)?) -> Void {
        urlComponents.path = path
        guard let url = urlComponents.url else { fatalError("No se pudo recuperar la url") }
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        var headers = request.allHTTPHeaderFields ?? [:]
        headers["Content-Type"] = "application/json"
        headers["apikey"] = self.apikey
        request.allHTTPHeaderFields = headers
        request.httpBody = post
        let config = URLSessionConfiguration.default
        let session = URLSession(configuration: config)
        let task = session.dataTask(with: request) { (responseData, response, responseError) in
            DispatchQueue.main.async {
                guard responseError == nil else {
                    completion?(nil, responseError!)
                    return
                }
                if let jsonObj = try? JSONSerialization.jsonObject(with: responseData!, options: .allowFragments) as? NSDictionary {
                    completion?(jsonObj, responseError)
                } else {
                    completion?(nil, responseError)
                }
            }
        }
        task.resume();
    }
}
