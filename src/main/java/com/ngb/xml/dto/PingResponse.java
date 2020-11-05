package com.ngb.xml.dto;

public class PingResponse {
    private Boolean hostReachable;
    private String message;

    public PingResponse(Boolean hostReachable, String message) {
        this.hostReachable = hostReachable;
        this.message = message;
    }

    public Boolean getHostReachable() {
        return this.hostReachable;
    }

    public void setHostReachable(Boolean hostReachable) {
        this.hostReachable = hostReachable;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString() {
        return "PingResponse{hostReachable=" + this.hostReachable + ", message=" + this.message + '}';
    }

    public PingResponse() {
    }
}
