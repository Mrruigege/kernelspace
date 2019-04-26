package com.rui.hyperlink.base;

/**
 * API服务类接口统一返回格式
 * @author xiaorui
 */
public class ApiResponse {

    private int code;

    private String message;

    private Object result;

    private boolean more;

    public ApiResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiResponse(int code, String message, Object result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public boolean isMore() {
        return more;
    }

    public void setMore(boolean more) {
        this.more = more;
    }

    public static ApiResponse ofSuccess(Object data) {
        return new ApiResponse(Status.SUCCESS.getCode(), Status.SUCCESS.getMessage(), data);
    }

    public static ApiResponse ofMessage(int code, String message) {
        return new ApiResponse(code, message, null);
    }

    public static ApiResponse ofStatus(Status status) {
        return new ApiResponse(status.getCode(), status.getMessage(), null);
    }

    public enum Status {
        SUCCESS(200, "OK"),
        ACCESS_DENIED(403, "ACCESS_DENIED"),
        NOT_FOUND(404, "NOT FOUND"),
        BAD_REQUEST(400, "BAD REQUEST"),
        INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR"),
        NOT_VALID_PARAM(40000, "NOT_VALID_PARAM"),
        NOT_SUPPORTED_OPTIONS(40001, "NOT_SUPPORTED_OPTIONS"),
        NOT_LOGIN(40002, "NOT_LOGIN"),
        LOGIN_ERR(40003, "LOGIN ERR");

        private int code;
        private String message;

        Status(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
