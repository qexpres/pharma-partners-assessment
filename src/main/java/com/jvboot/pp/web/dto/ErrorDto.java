package com.jvboot.pp.web.dto;

import com.jvboot.pp.exception.ApiException;
import org.springframework.http.HttpStatus;

public class ErrorDto {
    private final int httpCode;
    private final String httpStatus;
    private final String[] messages;

    public ErrorDto(ApiException e) {
        this(e.getStatus(), e.getMessage());
    }

    public ErrorDto(HttpStatus status, String... messages) {
        this.httpCode = status.value();
        this.httpStatus = status.getReasonPhrase();
        this.messages = messages;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public String[] getMessages() {
        return messages;
    }
}
