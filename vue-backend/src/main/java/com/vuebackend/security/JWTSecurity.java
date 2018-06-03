package com.vuebackend.security;

import java.io.UnsupportedEncodingException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTSecurity {
    
    private static String secret;

    @Value("${HMAC256Secret}")
    public void setSecret( String secret) {
        JWTSecurity.secret = secret;
    }

    public static String createJWTToken(String issuer) throws IllegalArgumentException, UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String token = JWT.create().withIssuer(issuer).sign(algorithm);
        System.out.println("token: " + token);
        return token;
    }
}