package com.vzert.smsmasivos.api.components;

import com.vzert.smsmasivos.api.models.*;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SMS {

    @POST("/sms/send/")
    Call<SendSMS> sendSMS(@Header("apikey") String apikey, @Body Map<String,String> params);

    @POST("/credits/consult/")
    Call<Credits> getCredits(@Header("apikey") String apikey, @Body Map<String,String> params);

    @POST("/reports/generate/")
    Call<Reports> getReports(@Header("apikey") String apikey, @Body Map<String,String> params);

    @POST("/contacts/add/")
    Call<Request> addContact(@Header("apikey") String apikey, @Body Map<String,String> params);

    @POST("/protected/JSON/phones/verification/start/")
    Call<Request> registry(@Header("apikey") String apikey, @Body Map<String,String> params);

    @POST("/protected/JSON/phones/verification/check/")
    Call<Request> validation(@Header("apikey") String apikey, @Body Map<String,String> params);

    @POST("/protected/JSON/phones/verification/resend/")
    Call<Request> resend(@Header("apikey") String apikey, @Body Map<String,String> params);

}
