package io.github.dlvaletnine.habitappapi.models;

// Simple response for HTTP requests
public class HttpResponse {
    private String response;
    private Integer responseCode;

    public HttpResponse(String response, Integer responseCode) {
        this.response = response;
        this.responseCode = responseCode;
    }

    public String getResponse() {
        return this.response;
    }

    public Integer getResponseCode() {
        return this.responseCode;
    }
}
