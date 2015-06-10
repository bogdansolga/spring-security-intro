package com.dialogdata.spring.security.intro.config;

import com.dialogdata.spring.security.intro.RunProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

@Configuration
public class WebConfig implements ServletContextInitializer {

    @Autowired
    private Environment environment;

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        if (environment.acceptsProfiles(RunProfile.DEVELOPMENT)) {
            initH2Console(servletContext);
        }
    }

    /** Initializes H2 console */
    private void initH2Console(final ServletContext servletContext) {
        ServletRegistration.Dynamic h2ConsoleServlet = servletContext.addServlet("H2Console", new org.h2.server.web.WebServlet());
        h2ConsoleServlet.addMapping("/console/*");
        h2ConsoleServlet.setInitParameter("-properties", "src/main/resources");
        h2ConsoleServlet.setLoadOnStartup(1);
    }
}
