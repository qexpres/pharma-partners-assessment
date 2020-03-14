package com.jvboot.pp.web.dto;

import com.jvboot.pp.exception.ApiException;

public class ErrorDto {
    private final int httpCode;
    private final String httpStatus;
    private final String message;

    public ErrorDto(ApiException e) {
        httpCode = e.getStatus().value();
        httpStatus = e.getStatus().getReasonPhrase();
        message = e.getMessage();
    }

    public int getHttpCode() {
        return httpCode;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
