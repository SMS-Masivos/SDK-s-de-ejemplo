package com.vzert.smsmasivos;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.vzert.smsmasivos.api.apismsmasivos;
import com.vzert.smsmasivos.api.models.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apismsmasivos sms = new apismsmasivos(this);

        sms.sendSMS("your_apikey","your_message","4771234567,4777654321,4771231234","your_country_code","your_campaign_name","language_selected","sandbox_mode", ( new Callback<SendSMS>() {
            @Override
            public void onResponse(Call<SendSMS> call, Response<SendSMS> response) {
                if (response.isSuccessful()) {
                    // TODO RESPONSE
                    Log.d("SMS_RESPONSE", response.body().getReferences().toString());
                }
            }
            @Override
            public void onFailure(Call<SendSMS> call, Throwable t) {
                // TODO ERROR
            }
        }));

        sms.getCredits("your_apikey","language_selected", ( new Callback<Credits>() {
            @Override
            public void onResponse(Call<Credits> call, Response<Credits> response) {
                if (response.isSuccessful()) {
                    // TODO RESPONSE
                    Log.d("SMS_RESPONSE", response.body().getCredit());
                }
            }
            @Override
            public void onFailure(Call<Credits> call, Throwable t) {
                // TODO ERROR
            }
        }));

        sms.getReports("your_apikey","2020-01-01 00:00:00","2020-01-31 23:59:59","language_selected","sandbox_mode", ( new Callback<Reports>() {
            @Override
            public void onResponse(Call<Reports> call, Response<Reports> response) {
                if (response.isSuccessful()) {
                    // TODO RESPONSE
                    Log.d("SMS_RESPONSE", response.body().getReport().toString());
                }
            }
            @Override
            public void onFailure(Call<Reports> call, Throwable t) {
                // TODO ERROR
            }
        }));

        sms.addContact("your_apikey","your_list_key","4771234567","your_contact_name","your_contact_email","language_selected", ( new Callback<Request>() {
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {
                if (response.isSuccessful()) {
                    // TODO RESPONSE
                    Log.d("SMS_RESPONSE", response.body().getMessage());
                }
            }
            @Override
            public void onFailure(Call<Request> call, Throwable t) {
                // TODO ERROR
            }
        }));

        sms.registryDoubleFactor("your_apikey","your_phone_number","your_country_code","your_code_length","your_company_name","language_selected", ( new Callback<Request>() {
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {
                if (response.isSuccessful()) {
                    // TODO RESPONSE
                    Log.d("SMS_RESPONSE", response.body().getMessage());
                }
            }
            @Override
            public void onFailure(Call<Request> call, Throwable t) {
                // TODO ERROR
            }
        }));

        sms.validationDoubleFactor("your_apikey","your_phone_number","client_verification_code","language_selected", ( new Callback<Request>() {
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {
                if (response.isSuccessful()) {
                    // TODO RESPONSE
                    Log.d("SMS_RESPONSE", response.body().getMessage());
                }
            }
            @Override
            public void onFailure(Call<Request> call, Throwable t) {
                // TODO ERROR
            }
        }));

        sms.forwardingDoubleFactor("your_apikey","your_phone_number","your_country_code","your_code_length","your_expiration_date","your_company_name","language_selected", ( new Callback<Request>() {
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {
                if (response.isSuccessful()) {
                    // TODO RESPONSE
                    Log.d("SMS_RESPONSE", response.body().getMessage());
                }
            }
            @Override
            public void onFailure(Call<Request> call, Throwable t) {
                // TODO ERROR
            }
        }));

    }
}
