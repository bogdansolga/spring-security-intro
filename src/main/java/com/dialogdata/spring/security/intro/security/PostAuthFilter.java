package com.dialogdata.spring.security.intro.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class PostAuthFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostAuthFilter.class);

    @Override
    @SuppressWarnings("unchecked")
    protected void successfulAuthentication(HttpServletRequest request, final HttpServletResponse response, FilterChain chain, Authentication authentication)
            throws IOException, ServletException {

        String userName = obtainUsername(request);

        LOGGER.info("On successful authentication - the user '{}' has logged in", userName);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Collection<GrantedAuthority> grantedAuthorities = (Collection<GrantedAuthority>) userDetails.getAuthorities();

        LOGGER.debug("The user '{}' has {} privileges - {}", userName, grantedAuthorities.size(), grantedAuthorities);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException)
            throws IOException, ServletException {

        String userName = obtainUsername(request);

        LOGGER.info("On unsuccessful authentication - the user '{}' has not logged in", userName);

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid username or password");
    }
}
