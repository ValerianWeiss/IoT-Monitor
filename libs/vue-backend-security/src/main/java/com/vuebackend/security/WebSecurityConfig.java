package com.vuebackend.security;

import com.netflix.discovery.EurekaClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${tokenHeader}")
    private String tokenHeader;

    @Value("${authServerName}")
    private String authServerName;

    @Autowired
    private EurekaClient client;
              

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        // Section for Resource Server
        webSecurity.ignoring().antMatchers("/user/**")
        .and()
        // Section for Auth Server
        .ignoring().antMatchers("/user**");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors()
            .and()
            .formLogin().disable().logout().disable()
            .csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            // Custom JWT based security filter
            .addFilterBefore(new JwtAuthorizationFilter(
                client, authServerName, tokenHeader), UsernamePasswordAuthenticationFilter.class)
            
            .addFilterBefore(new CorsHeadersFilter(), CorsFilter.class);
    }
}
