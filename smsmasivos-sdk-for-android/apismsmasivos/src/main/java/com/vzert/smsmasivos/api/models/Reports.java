package com.vzert.smsmasivos.api.models;

public class Reports {

    private String success;
    private String message;
    private int status;
    private String code;
    private Object report;

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

    public Object getReport() {
        return report;
    }

}
