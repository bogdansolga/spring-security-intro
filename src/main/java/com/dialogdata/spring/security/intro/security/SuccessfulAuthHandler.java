package com.dialogdata.spring.security.intro.security;

import com.dialogdata.spring.security.intro.data.dao.UserRepository;
import com.dialogdata.spring.security.intro.data.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.Collection;
import java.util.Optional;

public class SuccessfulAuthHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
                                        final Authentication authentication)
            throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_OK);

        final HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response);
        final Writer out = responseWrapper.getWriter();

        final String userName = request.getParameter("username");
        final Optional<User> user = userRepository.findOneByUserName(userName);

        if (user.isPresent()) {
            final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) authentication;
            final Collection<GrantedAuthority> authorities = usernamePasswordAuthenticationToken.getAuthorities();

            final String role = authorities.iterator().next().getAuthority().replace("ROLE_", "").toLowerCase();
            final AccountDetails accountDetails = new AccountDetails(role, user.get().getFirstName());
            final ObjectMapper mapper = new ObjectMapper();
            out.write(mapper.writeValueAsString(accountDetails));

            out.flush();
            out.close();
        }
    }

    private class AccountDetails implements Serializable {
        private String role;

        private String name;

        public AccountDetails(String role, String name) {
            this.role = role;
            this.name = name;
        }

        public String getRole() {
            return role;
        }

        public String getName() {
            return name;
        }
    }
}
