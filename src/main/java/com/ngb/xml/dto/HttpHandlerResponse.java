package com.ngb.xml.dto;

public class HttpHandlerResponse {
    private Boolean isSuccessful;
    private String message;

    public HttpHandlerResponse(Boolean isSuccessful, String message) {
        this.isSuccessful = isSuccessful;
        this.message = message;
    }

    public HttpHandlerResponse() {
    }

    public Boolean getIsSuccessful() {
        return this.isSuccessful;
    }

    public void setIsSuccessful(Boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString() {
        return "HttpHandlerResponse{isSuccessful=" + this.isSuccessful + ", message=" + this.message + '}';
    }
}
