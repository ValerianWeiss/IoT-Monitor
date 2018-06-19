package com.vuebackend.security;

import java.io.IOException;
import java.security.InvalidParameterException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.vuebackend.communication.TokenRequest;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private String tokenHeader;
    private String tokenValidationUrl;
    private RestTemplate restTemplate;

    public JwtAuthorizationFilter(String tokenHeader, String tokenValidationUrl) {
        this.tokenHeader = tokenHeader;
        this.tokenValidationUrl = tokenValidationUrl;
        this.restTemplate = new RestTemplate();
        System.out.println("construced");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String requestHeader = request.getHeader(this.tokenHeader);

        String authToken = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);

            HttpEntity<TokenRequest> tokenRequest = new HttpEntity<>(new TokenRequest(authToken));

            boolean tokenIsValid = 
                this.restTemplate.postForObject(this.tokenValidationUrl, tokenRequest, Boolean.class);

            if (!tokenIsValid) {
                throw new IOException("Token was invalid!");
            }
        } else {
            throw new InvalidParameterException("No token could be found in the header or is invalid");
        }

        System.out.println("Token was valid");
        chain.doFilter(request, response);
    }
}
