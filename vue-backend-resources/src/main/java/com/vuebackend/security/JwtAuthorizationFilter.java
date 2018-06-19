package com.vuebackend.security;

import java.io.IOException;
import java.security.InvalidParameterException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.http.HttpEntity;

import com.vuebackend.communication.ResponseMessage;
import com.vuebackend.communication.TokenRequest;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private String tokenHeader;

    @Autowired
    private RestTemplate rest;

    public JwtAuthorizationFilter(String tokenHeader) {
        this.tokenHeader = tokenHeader;
    }

    @Bean
    private RestTemplate setRestTemplate() {
        ClientHttpRequestFactory requestFactory = getClientHttpRequestFactory();
        return new RestTemplate(requestFactory);
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String requestHeader = request.getHeader(this.tokenHeader);

        String authToken = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);
        

            HttpEntity<TokenRequest> tokenRequest = new HttpEntity<>(new TokenRequest(authToken));
            if(this.rest == null) {
                System.out.println("rest was null");
            }

            if("${TokenValidationUrl}" == null) {
                System.out.println("rest was null");
            }

            if(tokenRequest == null) {
                System.out.println("request was null");
            }

            if(ResponseMessage.class == null) {
                System.out.println("class was null");
            }

            ResponseMessage responseMessage = this.rest.postForObject("${TokenValidationUrl}", tokenRequest, ResponseMessage.class);

            if(responseMessage == null){
                System.out.println("was nul");
            }
            if(responseMessage.getSuccess()) {
                throw new IOException("token was invalid");
            }
        } else {
            throw new InvalidParameterException("No token could be found in the header or is invalid");
        }

        System.out.println("Token was valid");
        chain.doFilter(request, response);
    }
}
