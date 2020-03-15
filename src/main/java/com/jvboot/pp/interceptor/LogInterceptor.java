package com.jvboot.pp.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Component
public class LogInterceptor extends HandlerInterceptorAdapter {
    private static String REQUEST_ID = "LOG_REQUEST_ID";

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler
    ) throws Exception {
        UUID uuid = UUID.randomUUID();
        request.setAttribute(REQUEST_ID, uuid);
        String shortId = shortId(uuid);
        log.info("Request  [{}] {} {}", shortId, request.getMethod(), request.getRequestURI());
        return true;
    }

    @Override
    public void afterCompletion(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler,
        Exception ex
    ) throws Exception {
        String shortId = shortId(request.getAttribute(REQUEST_ID));
        int status = response.getStatus();
        String format = "Response [{}] {}";
        if (ex != null) {
            log.error(format, shortId, ex.getMessage());
        } else if (status >= 500) {
            log.error(format, shortId, status);
        } else if (status >= 400) {
            log.warn(format, shortId, status);
        } else {
            log.info(format, shortId, status);
        }
    }

    private String shortId(Object uuid) {
        if (uuid instanceof UUID) {
            return ((UUID) uuid).toString().substring(0, 8);
        }
        return "";
    }
}
