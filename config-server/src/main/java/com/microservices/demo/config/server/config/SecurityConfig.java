package com.microservices.demo.config.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * SecurityConfig class for excluding some paths from authentication
 * */

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/actuator/**")
                .antMatchers("/encrypt/**")
                .antMatchers("/decrypt/**");
        super.configure(web);
    }
}
