package com.vzert.smsmasivos.api.models;

public class SendSMS {

    private String success;
    private String message;
    private int status;
    private String code;
    private Object references;

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public Object getReferences() {
        return references;
    }

}
