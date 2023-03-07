package com.rest.webservices.restfulweb.services.exception;

import java.time.LocalDate;

public class ErrorDetails {
    //timestamp
    //message
    //details

    private LocalDate timestap;
    private String message;
    private String details;

    public ErrorDetails(LocalDate timestap, String message, String details) {
        super();
        this.timestap = timestap;
        this.message = message;
        this.details = details;
    }

    public LocalDate getTimestap() {
        return timestap;
    }

    public void setTimestap(LocalDate timestap) {
        this.timestap = timestap;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
