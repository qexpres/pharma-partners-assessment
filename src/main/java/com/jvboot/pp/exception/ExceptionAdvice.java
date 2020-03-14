package com.jvboot.pp.exception;

import com.jvboot.pp.web.dto.ErrorDto;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Intercepts all exceptions thrown by the application.
 */
@RestControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    /**
     * Catch all exceptions of type ApiException as we know how to represent them.
     *
     * @param e the ApiException
     * @return a nicely formatted error
     */
    @ExceptionHandler(ApiException.class)
    public ErrorDto apiException(ApiException e) {
        return new ErrorDto(e);
    }
}
