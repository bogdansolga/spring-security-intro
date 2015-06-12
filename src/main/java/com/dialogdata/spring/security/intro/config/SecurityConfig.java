package com.dialogdata.spring.security.intro.config;

import com.dialogdata.spring.security.intro.security.NotFoundAccessDeniedHandler;
import com.dialogdata.spring.security.intro.security.SuccessfulAuthHandler;
import com.dialogdata.spring.security.intro.security.UnsuccessfulAuthHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@AutoConfigureAfter(DataSourceConfig.class)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter implements EnvironmentAware {

    private RelaxedPropertyResolver propertyResolver;

    @Autowired
    private DataSource dataSource;

    @Override
    public void setEnvironment(Environment environment) {
        this.propertyResolver = new RelaxedPropertyResolver(environment, "spring.security.auth.");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder()).init(auth);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.formLogin()
            .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
            .defaultSuccessUrl("/#home")
            .failureUrl("/#home")
            .successHandler(successfulAuthHandler())
            .failureHandler(unsuccessfulAuthHandler())
        .permitAll();

        http.csrf().disable();
        http.headers()
            .xssProtection().disable()
            .frameOptions().disable()
            .contentTypeOptions().disable()
            .cacheControl().disable();

        http.sessionManagement()
            .sessionAuthenticationErrorUrl("/#")
            .invalidSessionUrl("/#");

        final ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorizeRequests = http.authorizeRequests();
        authorizeRequests.antMatchers(HttpMethod.GET, "/*/user/**").access("authenticated AND hasRole('ROLE_MANAGER')");
        authorizeRequests.antMatchers(HttpMethod.GET, "/*/product/**").access("authenticated AND hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')");

        http.logout()
            .logoutUrl("/logout")
            .invalidateHttpSession(true)
            .logoutSuccessUrl("/#home")
            .deleteCookies("JSESSIONID")
            .permitAll();

        http.exceptionHandling().accessDeniedHandler(new NotFoundAccessDeniedHandler());
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/index.html")
                .antMatchers("/templates/**")
                .antMatchers("/js/**")
                .antMatchers("/css/**");
    }

    @Bean
    public SuccessfulAuthHandler successfulAuthHandler() {
        return new SuccessfulAuthHandler();
    }

    @Bean
    public UnsuccessfulAuthHandler unsuccessfulAuthHandler() {
        return new UnsuccessfulAuthHandler();
    }

    @Override
    public UserDetailsService userDetailsService() {
        JdbcDaoImpl userDetailsService = new JdbcDaoImpl();

        userDetailsService.setDataSource(dataSource);
        userDetailsService.setUsersByUsernameQuery(propertyResolver.getProperty("usersByUsernameQuery"));
        userDetailsService.setAuthoritiesByUsernameQuery(propertyResolver.getProperty("authoritiesByUsernameQuery"));

        return userDetailsService;
    }
}
