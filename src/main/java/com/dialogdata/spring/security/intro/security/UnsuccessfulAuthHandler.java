package com.dialogdata.spring.security.intro.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.Writer;

public class UnsuccessfulAuthHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response);
        final Writer out = responseWrapper.getWriter();

        out.write("{\"message\": \"Incorrect username or password\"}");
        out.flush();
    }
}
