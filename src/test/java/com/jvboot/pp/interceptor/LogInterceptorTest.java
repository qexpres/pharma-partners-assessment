package com.jvboot.pp.interceptor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.jvboot.pp.interceptor.LogInterceptor.REQUEST_ID;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class LogInterceptorTest {
    private static final String method = "HEAD";
    private static final String uri = "/test";
    private static final String session = "a41fee81-abdd411f";

    private static final int ok = HttpStatus.NO_CONTENT.value();
    private static final int clientError = HttpStatus.I_AM_A_TEAPOT.value();
    private static final int serverError = HttpStatus.LOOP_DETECTED.value();

    private static final Exception ex = new Exception("MY BAD!!!");

    private final LogInterceptor logInterceptor;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private Logger log;

    @Autowired
    public LogInterceptorTest(LogInterceptor logInterceptor) {
        this.logInterceptor = logInterceptor;
    }

    @BeforeEach
    public void setMocks() {
        request = mock(HttpServletRequest.class);
        when(request.getMethod()).thenReturn(method);
        when(request.getRequestURI()).thenReturn(uri);
        when(request.getAttribute(eq(REQUEST_ID))).thenReturn(session);

        response = mock(HttpServletResponse.class);

        log = mock(Logger.class);
        logInterceptor.log = log;
    }

    @Test
    public void preHandleShouldAlwaysLogRequest() {
        logInterceptor.preHandle(request, null, null);

        Mockito.verify(log).info(eq("Request  [{}] {} {}"), matches("\\w+"), eq(method), eq(uri));
    }

    @Test
    public void afterCompletionShouldLogExceptions() {
        logInterceptor.afterCompletion(request, response, null, ex);

        Mockito.verify(log).error("Response [{}] {}", session.substring(0, 8), ex.getMessage());
    }

    @Test
    public void afterCompletionShouldLogServerErrors() {
        when(response.getStatus()).thenReturn(serverError);
        logInterceptor.afterCompletion(request, response, null, null);

        Mockito.verify(log).error("Response [{}] {}", session.substring(0, 8), serverError);
    }

    @Test
    public void afterCompletionShouldLogClientErrors() {
        when(response.getStatus()).thenReturn(clientError);
        logInterceptor.afterCompletion(request, response, null, null);

        Mockito.verify(log).warn("Response [{}] {}", session.substring(0, 8), clientError);
    }

    @Test
    public void afterCompletionShouldLogOkStatus() {
        when(response.getStatus()).thenReturn(ok);
        logInterceptor.afterCompletion(request, response, null, null);

        Mockito.verify(log).info("Response [{}] {}", session.substring(0, 8), ok);
    }
}