package com.jvboot.pp.web.dto;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorDtoTest {
    private static final HttpStatus status = HttpStatus.I_AM_A_TEAPOT;
    private static final String message1 = HttpStatus.I_AM_A_TEAPOT.getReasonPhrase();
    private static final String message2 = HttpStatus.EXPECTATION_FAILED.getReasonPhrase();
    private static final String message3 = HttpStatus.URI_TOO_LONG.getReasonPhrase();

    @Test
    public void constructorShouldSetHttpFields() {
        ErrorDto error = new ErrorDto(status);
        assertEquals(error.getHttpCode(), status.value());
        assertEquals(error.getHttpStatus(), status.getReasonPhrase());
    }

    @Test
    public void constructorShouldSetMessages() {
        ErrorDto error1 = new ErrorDto(status);
        assertThat(error1.getMessages()).isEmpty();

        ErrorDto error2 = new ErrorDto(status, message1, message2, message3);
        assertThat(error2.getMessages())
            .hasSize(3)
            .isEqualTo(new String[] {message1, message2, message3});
    }
}