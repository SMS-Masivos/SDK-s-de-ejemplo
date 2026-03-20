import express from 'express';
import path from 'path';
import axios from 'axios';
import { fileURLToPath } from 'url';

const app = express();
const PORT = 3000;

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

// Middleware para JSON
app.use(express.json());
// Servir archivos estáticos
app.use('/static', express.static(path.join(__dirname, 'public')));

// Ruta principal: index.html
app.get('/', (req, res) => {
  res.sendFile(path.join(__dirname, 'public/index.html'));
});

// Ruta para 2factorVerification.html
app.get('/2factorVerification', (req, res) => {
  res.sendFile(path.join(__dirname, 'public/2factorVerification.html'));
});

// Función auxiliar para llamar a la API de smsmasivos
async function smsmasivosApiPost(endpoint, apikey, params, responseFormat = 'json') {
  const url = `https://api.smsmasivos.com.mx/${endpoint}`;
  try {
    const response = await axios.post(url, new URLSearchParams(params), {
      headers: { 'apikey': apikey }
    });
    return response.data;
  } catch (error) {
    if (responseFormat === 'json') {
      return { success: false, message: 'Error en la conexión: ' + error.message };
    } else {
      return `<error>Error en la conexión: ${error.message}</error>`;
    }
  }
}

// Endpoint para enviar SMS
app.post('/send_sms', async (req, res) => {
  const { apikey, numbers, message, country_code, sandbox = 0 } = req.body;
  if (!apikey || !numbers || !message || !country_code) {
    return res.json({ success: false, message: 'Parámetros insuficientes.' });
  }
  const params = {
    message,
    numbers,
    country_code,
    name: 'Campaña demo ' + new Date().toLocaleString('sv'),
    sandbox
  };
  const response = await smsmasivosApiPost('sms/send', apikey, params);
  res.json(response);
});

// Endpoint para registrar 2FA
app.post('/register_2fa', async (req, res) => {
  const { apikey, company, phone_number, country_code, code_length = 4, format = 'json' } = req.body;
  if (!apikey || !company || !phone_number || !country_code) {
    return res.json({ success: false, message: 'Parámetros insuficientes.' });
  }
  const params = {
    phone_number,
    country_code,
    company,
    code_length,
    template: 'a',
    format
  };
  const endpoint = `protected/${format}/phones/verification/start`;
  const response = await smsmasivosApiPost(endpoint, apikey, params, format);
  if (format === 'json') {
    res.json(response);
  } else {
    res.type('text/xml').send(response);
  }
});

// Endpoint para validar 2FA
app.post('/validate_2fa', async (req, res) => {
  const { apikey, phone_number, verification_code, format = 'json' } = req.body;
  if (!apikey || !phone_number || !verification_code) {
    return res.json({ success: false, message: 'Parámetros insuficientes.' });
  }
  const params = {
    phone_number,
    verification_code,
    format
  };
  const endpoint = `protected/${format}/phones/verification/check`;
  const response = await smsmasivosApiPost(endpoint, apikey, params, format);
  if (format === 'json') {
    res.json(response);
  } else {
    res.type('text/xml').send(response);
  }
});

// Iniciar el servidor
app.listen(PORT, () => {
  console.log(`Servidor corriendo en http://localhost:${PORT}`);
});
