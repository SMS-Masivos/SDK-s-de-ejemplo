from fastapi import FastAPI
from fastapi.responses import JSONResponse, PlainTextResponse, Response, FileResponse
from fastapi.staticfiles import StaticFiles
from pydantic import BaseModel
from typing import Optional
from datetime import datetime
import requests

# --------- CONFIGURACIÓN ---------
STATIC_DIR = "static"

# --------- MODELOS ---------
class SendSMSRequest(BaseModel):
    apikey: str
    numbers: str
    message: str
    country_code: str
    sandbox: int = 0

class Register2FARequest(BaseModel):
    apikey: str
    company: str
    phone_number: str
    country_code: str
    code_length: int = 4
    format: str = "json"

class Validate2FARequest(BaseModel):
    apikey: str
    phone_number: str
    verification_code: str
    format: str = "json"

# --------- APP ---------
app = FastAPI()
app.mount("/static", StaticFiles(directory=STATIC_DIR, html=True), name="static")

@app.get("/")
def root():
    return FileResponse(f"{STATIC_DIR}/index.html")

@app.get("/2factorVerification")
def twofa():
    return FileResponse(f"{STATIC_DIR}/2factorVerification.html")

# --------- FUNCIONES ---------
def smsmasivos_api_post(endpoint, apikey, params, response_format="json"):
    url = f"https://api.smsmasivos.com.mx/{endpoint}"
    headers = {"apikey": apikey}
    try:
        r = requests.post(url, data=params, headers=headers, timeout=15)
        r.raise_for_status()
        return r.text
    except requests.RequestException as e:
        if response_format == "json":
            return '{"success": false, "message": "Error en la conexión: %s"}' % str(e)
        else:
            return "<error>Error en la conexión: %s</error>" % str(e)

# --------- ENDPOINTS ---------

@app.post("/send_sms")
async def send_sms(body: SendSMSRequest):
    params = {
        "message": body.message,
        "numbers": body.numbers,
        "country_code": body.country_code,
        "name": f"Campaña demo {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}",
        "sandbox": body.sandbox
    }
    response = smsmasivos_api_post("sms/send", body.apikey, params)
    return Response(content=response, media_type="application/json")

@app.post("/register_2fa")
async def register_2fa(body: Register2FARequest):
    response_format = (body.format or "json").strip().lower()
    params = {
        "phone_number": body.phone_number,
        "country_code": body.country_code,
        "company": body.company,
        "code_length": body.code_length,
        "template": "a",
        "format": response_format
    }
    endpoint = f"protected/{response_format}/phones/verification/start"
    response = smsmasivos_api_post(endpoint, body.apikey, params, response_format)
    if response_format == "json":
        return Response(content=response, media_type="application/json")
    else:
        return PlainTextResponse(content=response, media_type="text/xml")

@app.post("/validate_2fa")
async def validate_2fa(body: Validate2FARequest):
    response_format = (body.format or "json").strip().lower()
    params = {
        "phone_number": body.phone_number,
        "verification_code": body.verification_code,
        "format": response_format
    }
    endpoint = f"protected/{response_format}/phones/verification/check"
    response = smsmasivos_api_post(endpoint, body.apikey, params, response_format)
    if response_format == "json":
        return Response(content=response, media_type="application/json")
    else:
        return PlainTextResponse(content=response, media_type="text/xml")
