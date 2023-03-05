package com.stefanini.exception.response;

import java.time.LocalDateTime;

public class ExceptionResponse {
    private String message;
    private String details;

    private LocalDateTime timestamp;

    public ExceptionResponse(String message, String details) {
        this.message = message;
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
