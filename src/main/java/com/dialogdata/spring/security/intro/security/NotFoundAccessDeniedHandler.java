package com.dialogdata.spring.security.intro.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NotFoundAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(final HttpServletRequest request, final HttpServletResponse response, final AccessDeniedException e)
            throws IOException, ServletException {
        if (!response.isCommitted()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
