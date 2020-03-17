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
    public static String REQUEST_ID = "LOG_REQUEST_ID";

    Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Log the request and attach an identifier to it for later reference. The response and handler are irrelevant for
     * now.
     *
     * @param request  the request object to log
     * @param response the response object
     * @param handler  the handler
     * @return
     */
    @Override
    public boolean preHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler
    ) {
        String uuid = UUID.randomUUID().toString();
        request.setAttribute(REQUEST_ID, uuid);
        String shortId = shortId(uuid);
        log.info("Request  [{}] {} {}", shortId, request.getMethod(), request.getRequestURI());
        return true;
    }

    /**
     * Log each response after completing the request. For now it only logs to which requests it belongs by displaying
     * the attached identifier and it shows the HTTP response code. If an exception is passed along, it displays the
     * exception message instead.
     *
     * @param request  the original request object
     * @param response the response object
     * @param handler  the handler
     * @param ex       the exception
     */
    @Override
    public void afterCompletion(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler,
        Exception ex
    ) {
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

    /**
     * Takes a stringified UUID and only shows the first 8 characters as a shorthash.
     *
     * @param uuid the stringified UUID
     * @return a shorthash
     */
    private String shortId(Object uuid) {
        if (uuid instanceof String) {
            return ((String) uuid).substring(0, 8);
        }
        return "";
    }
}
