package io.github.dlvalentine.habitappapi.responses;

// Simple custom response for HTTP requests
public class HttpResponse {
    private String message;
    private Integer status;
    private String error;

    public HttpResponse(String message, Integer status) {
        this.message = message;
        this.status = status;
        this.error = null;
    }

    public HttpResponse(String message, Integer status, String error) {
        this.message = message;
        this.status = status;
        this.error = error;
    }

    public String getMessage() {
        return this.message;
    }

    public Integer getStatus() {
        return this.status;
    }

    public String getError() {
        return this.error;
    }
}
