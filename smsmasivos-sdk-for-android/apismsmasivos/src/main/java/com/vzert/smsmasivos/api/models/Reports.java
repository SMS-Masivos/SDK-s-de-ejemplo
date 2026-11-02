package com.vzert.smsmasivos.api.models;

public class Reports {

    private String success;
    private String message;
    private int status;
    private String code;
    private Object report;
    private Pagination pagination;

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

    public Pagination getPagination() {
        return pagination;
    }

    public static class Pagination {

        private boolean has_more;
        private String next_cursor;

        public boolean getHasMore() {
            return has_more;
        }

        public String getNextCursor() {
            return next_cursor;
        }

    }

}
