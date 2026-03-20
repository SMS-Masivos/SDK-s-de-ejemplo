package com.vzert.smsmasivos.api;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vzert.smsmasivos.api.components.*;
import com.vzert.smsmasivos.api.models.*;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class apismsmasivos {

    Context mContext;
    public apismsmasivos(Context context) { this.mContext = context; }

    Gson GS = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
    Retrofit RF = new Retrofit.Builder().baseUrl("https://api.smsmasivos.com.mx").addConverterFactory(GsonConverterFactory.create(GS)).build();
    SMS sms = RF.create(SMS.class);

    public void sendSMS(String apikey, String message, String numbers, String country_code, String campaign_name, String language, String sandbox, Callback callback) {

        Map<String,String> params = new HashMap();
        params.put("message", message);
        params.put("numbers", numbers);
        params.put("country_code", country_code);
        params.put("name", campaign_name);
        params.put("lang", validLanguage(language));
        params.put("sandbox", validSandbox(sandbox));

        Map<String,String> validation = validSendSMS(params);
        if (validation.get("success").equals("true")) {
            Call<SendSMS> call = sms.sendSMS(apikey,params);
            call.enqueue(callback);
        } else printError(validation.get("message"));
    }

    public void getCredits(String apikey, String language, Callback callback) {

        Map<String, String> params = new HashMap();
        params.put("lang", validLanguage(language));

        Call<Credits> call = sms.getCredits(apikey,params);
        call.enqueue(callback);
    }

    public void getReports(String apikey, String start_date, String end_date, String language, String sandbox, Callback callback) {

        Map<String, String> params = new HashMap();
        params.put("start_date", start_date);
        params.put("end_date", end_date);
        params.put("lang", validLanguage(language));
        params.put("sandbox", validSandbox(sandbox));

        Map<String,String> v = validReports(params);
        if (v.get("success").equals("true")) {
            Call<Reports> call = sms.getReports(apikey,params);
            call.enqueue(callback);
        } else printError(v.get("message"));
    }

    public void addContact(String apikey, String contact_list, String number, String name, String email, String language, Callback callback) {

        Map<String, String> params = new HashMap();
        params.put("list_key", contact_list);
        params.put("number", number);
        params.put("name", name);
        params.put("email", email);
        params.put("lang", validLanguage(language));

        Map<String,String> v = validAddContact(params);
        if (v.get("success").equals("true")) {
            Call<Request> call = sms.addContact(apikey,params);
            call.enqueue(callback);
        } else printError(v.get("message"));
    }

    public void registryDoubleFactor(String apikey, String phone_number, String country_code, String code_length, String company, String language, Callback callback) {

        Map<String, String> params = new HashMap();
        params.put("phone_number", phone_number);
        params.put("country_code", country_code);
        params.put("code_length", code_length);
        params.put("locale", validLanguage(language));
        params.put("company", company);

        Map<String,String> v = validRegistry(params);
        if (v.get("success").equals("true")) {
            Call<Request> call = sms.registry(apikey,params);
            call.enqueue(callback);
        } else printError(v.get("message"));
    }

    public void validationDoubleFactor(String apikey, String phone_number, String verification_code, String language, Callback callback) {

        Map<String, String> params = new HashMap();
        params.put("phone_number", phone_number);
        params.put("verification_code", verification_code);
        params.put("locale", validLanguage(language));

        Map<String,String> v = validValidation(params);
        if (v.get("success").equals("true")) {
            Call<Request> call = sms.validation(apikey,params);
            call.enqueue(callback);
        } else printError(v.get("message"));
    }

    public void forwardingDoubleFactor(String apikey, String phone_number, String country_code, String code_length, String expiration_date, String company, String language, Callback callback) {

        Map<String, String> params = new HashMap();
        params.put("resend", "1");
        params.put("phone_number", phone_number);
        params.put("country_code", country_code);
        params.put("code_length", code_length);
        params.put("expiration_date", expiration_date);
        params.put("company", company);
        params.put("locale", validLanguage(language));

        Map<String,String> v = validResend(params);
        if (v.get("success").equals("true")) {
            Call<Request> call = sms.resend(apikey,params);
            call.enqueue(callback);
        } else printError(v.get("message"));
    }

    private Map<String,String> validSendSMS (Map<String,String> map) {
        Map<String,String> m = new HashMap();
        m.put("success", "true");
        if (!validEmpty(map.get("message")))        { m.put("success","false"); m.put("message","Wrong message text"); }
        if (!validEmpty(map.get("numbers")))        { m.put("success","false"); m.put("message","Wrong numbers"); }
        if (!validEmpty(map.get("country_code")))   { m.put("success","false"); m.put("message","Wrong region number"); }
        if (!validEmpty(map.get("name")))           { m.put("success","false"); m.put("message","Wrong company name"); }
        return m;
    }

    private Map<String,String> validReports (Map<String,String> map) {
        Map<String,String> m = new HashMap();
        m.put("success", "true");
        if (!validEmpty(map.get("start_date")))     { m.put("success","false"); m.put("message","Invalid start date"); }
        if (!validEmpty(map.get("end_date")))       { m.put("success","false"); m.put("message","Invalid end date"); }
        return m;
    }

    private Map<String,String> validAddContact (Map<String,String> map) {
        Map<String,String> m = new HashMap();
        m.put("success", "true");
        if (!validEmpty(map.get("list_key")))       { m.put("success","false"); m.put("message","Wrong contact list"); }
        if (!validEmpty(map.get("number")))         { m.put("success","false"); m.put("message","Wrong number"); }
        if (!validEmpty(map.get("name")))           { m.put("success","false"); m.put("message","Incorrect contact name"); }
        if (!validEmpty(map.get("email")))          { m.put("success","false"); m.put("message","Incorrect contact email"); }
        return m;
    }

    private Map<String,String> validRegistry (Map<String,String> map) {
        Map<String,String> m = new HashMap();
        m.put("success", "true");
        if (!validEmpty(map.get("phone_number")))   { m.put("success","false"); m.put("message","Wrong number"); }
        if (!validEmpty(map.get("country_code")))   { m.put("success","false"); m.put("message","Wrong Region Number"); }
        if (!validEmpty(map.get("code_length")))    { m.put("success","false"); m.put("message","Wrong code length"); }
        if (!validEmpty(map.get("company")))        { m.put("success","false"); m.put("message","Wrong company name"); }
        return m;
    }

    private Map<String,String> validValidation (Map<String,String> map) {
        Map<String,String> m = new HashMap();
        m.put("success", "true");
        if (!validEmpty(map.get("phone_number")))     { m.put("success","false"); m.put("message","Wrong number"); }
        if (!validEmpty(map.get("verification_code"))){ m.put("success","false"); m.put("message","Incorrect verification code"); }
        return m;
    }

    private Map<String,String> validResend (Map<String,String> map) {
        Map<String,String> m = new HashMap();
        m.put("success", "true");
        if (!validEmpty(map.get("phone_number")))   { m.put("success","false"); m.put("message","Wrong number"); }
        if (!validEmpty(map.get("country_code")))   { m.put("success","false"); m.put("message","Wrong region number"); }
        if (!validEmpty(map.get("code_length")))    { m.put("success","false"); m.put("message","Wrong code length"); }
        if (!validEmpty(map.get("company")))        { m.put("success","false"); m.put("message","Wrong company name"); }
        if (!validEmpty(map.get("expiration_date"))){ m.put("success","false"); m.put("message","Wrong expiration date"); }
        return m;
    }

    private boolean validEmpty(String T) {
        return (T!=null&&!T.isEmpty());
    }

    private String validSandbox(String S) {
        return (!S.equals("0")&&!S.equals("1"))?"0":S;
    }

    private String validLanguage(String L) {
        return (!L.equals("es")&&!L.equals("en"))?"es":L;
    }

    private void printError(String message) {
        Log.e("##### SMS-ERROR #####", message);
    }

}
