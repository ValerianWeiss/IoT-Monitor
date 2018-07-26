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

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.vuebackend.communication.TokenRequest;
import com.vuebackend.communication.registry.Registry;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private String tokenHeader;
    private String tokenValidationUrl;
    private RestTemplate restTemplate;


    public JwtAuthorizationFilter(EurekaClient client, String authServerName, String tokenHeader) {
        this.tokenHeader = tokenHeader;
        this.restTemplate = new RestTemplate();

        System.out.println("Waiting for auth Server");
        long timeout = System.currentTimeMillis() + 1000*60;

        while(System.currentTimeMillis() < timeout) {
            InstanceInfo service = Registry.getInstance(client, authServerName);
            if(service != null) {
                this.tokenValidationUrl = service.getHostName() + ":" + service.getPort() + "/user/isTokenValid";
                break;
            }
        }
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

        chain.doFilter(request, response);
    }
}
