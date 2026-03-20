<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

// Permitir CORS solo para pruebas locales (puedes eliminar esta línea si no la necesitas)
header('Access-Control-Allow-Origin: *');
header('Content-Type: application/json');

if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    http_response_code(405);
    echo json_encode(['success' => false, 'message' => 'Método no permitido.']);
    exit;
}

$cmd = strtolower(trim($_POST['cmd'] ?? ''));

switch ($cmd) {
    case 'send':
        // SMS normal (ya lo tienes)
        $apikey       = trim($_POST['apikey'] ?? '');
        $numbers      = trim($_POST['numbers'] ?? '');
        $message      = trim($_POST['message'] ?? '');
        $country_code = trim($_POST['country_code'] ?? '');
        $sandbox      = isset($_POST['sandbox']) ? (int)$_POST['sandbox'] : 0;

        if ($apikey === '' || $numbers === '' || $message === '' || $country_code === '') {
            echo json_encode(['success' => false, 'message' => 'Parámetros insuficientes.']);
            exit;
        }

        $params = [
            'message'      => $message,
            'numbers'      => $numbers,
            'country_code' => $country_code,
            'name'         => 'Campaña demo ' . date('Y-m-d H:i:s'),
            'sandbox'      => $sandbox
        ];

        echo smsmasivos_api_post('sms/send', $apikey, $params);
        break;

    case 'register':
        // Enviar código 2FA
        $apikey       = trim($_POST['apikey'] ?? '');
        $company      = trim($_POST['company'] ?? '');
        $phone_number = trim($_POST['phone_number'] ?? '');
        $country_code = trim($_POST['country_code'] ?? '');
        $code_length  = intval($_POST['code_length'] ?? 4);
        $format       = strtolower(trim($_POST['format'] ?? 'json'));

        if ($apikey === '' || $company === '' || $phone_number === '' || $country_code === '') {
            echo json_encode(['success' => false, 'message' => 'Parámetros insuficientes.']);
            exit;
        }

        $params = [
            'phone_number'   => $phone_number,
            'country_code'   => $country_code,
            'company'        => $company,
            'code_length'    => $code_length,
            'template'       => 'a', // Puedes permitir elegir esto en el form si lo deseas
            'format'         => $format
        ];

        // La ruta requiere el formato en la URL
        $endpoint = "protected/{$format}/phones/verification/start";
        // La APIKey va en header y el formato es POST
        $response = smsmasivos_api_post($endpoint, $apikey, $params, $format);

        // Si es JSON, fuerza respuesta como JSON
        if ($format === 'json') {
            header('Content-Type: application/json');
            echo $response;
        } else {
            header('Content-Type: text/xml');
            echo $response;
        }
        break;

    case 'validate':
        // Validar código 2FA
        $apikey            = trim($_POST['apikey'] ?? '');
        $phone_number      = trim($_POST['phone_number'] ?? '');
        $verification_code = trim($_POST['verification_code'] ?? '');
        $format            = strtolower(trim($_POST['format'] ?? 'json'));

        if ($apikey === '' || $phone_number === '' || $verification_code === '') {
            echo json_encode(['success' => false, 'message' => 'Parámetros insuficientes.']);
            exit;
        }

        $params = [
            'phone_number'      => $phone_number,
            'verification_code' => $verification_code,
            'format'            => $format
        ];

        $endpoint = "protected/{$format}/phones/verification/check";
        $response = smsmasivos_api_post($endpoint, $apikey, $params, $format);

        if ($format === 'json') {
            header('Content-Type: application/json');
            echo $response;
        } else {
            header('Content-Type: text/xml');
            echo $response;
        }
        break;

    default:
        echo json_encode(['success' => false, 'message' => 'Comando no reconocido.']);
        break;
}

// Función para llamar a la API de smsmasivos
function smsmasivos_api_post($endpoint, $apikey, $params, $format = 'json') {
    $headers = [
        'apikey: ' . $apikey
    ];

    $ch = curl_init();
    curl_setopt_array($ch, [
        CURLOPT_URL => "https://api.smsmasivos.com.mx/$endpoint",
        CURLOPT_SSL_VERIFYPEER => true,
        CURLOPT_HEADER => 0,
        CURLOPT_HTTPHEADER => $headers,
        CURLOPT_POST => 1,
        CURLOPT_POSTFIELDS => http_build_query($params),
        CURLOPT_RETURNTRANSFER => 1
    ]);
    $response = curl_exec($ch);
    $error = curl_error($ch);
    curl_close($ch);

    if ($error) {
        if ($format === 'json') {
            return json_encode(['success' => false, 'message' => 'Error en la conexión: ' . $error]);
        } else {
            return "<error>Error en la conexión: " . htmlspecialchars($error) . "</error>";
        }
    } else {
        return $response;
    }
}
