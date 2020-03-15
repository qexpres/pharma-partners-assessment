package com.jvboot.pp.exception;

import com.jvboot.pp.web.dto.ErrorDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
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
    public ResponseEntity<ErrorDto> handleApiException(ApiException e) {
        return new ResponseEntity<>(new ErrorDto(e), e.getStatus());
    }

    /**
     * Custom error for validation errors.
     *
     * @param ex      the original validation exception
     * @param headers the headers to set in the response
     * @param status  the http status to use in the response
     * @param request the original request
     * @return a nicely formatted validation error
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request
    ) {
        return new ResponseEntity<>(new ErrorDto(status, messages(ex.getBindingResult())), status);
    }

    private String[] messages(BindingResult bindingResult) {
        return bindingResult.getAllErrors()
            .stream()
            .map(objectError -> {
                FieldError fieldError = (FieldError) objectError;
                return fieldError.getField() + ": " + fieldError.getDefaultMessage();
            })
            .toArray(String[]::new);
    }
}
