package com.vuebackend.security;

import java.io.UnsupportedEncodingException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTTokenUtils {

    private static String secret;

    @Value("${HMAC256Secret}")
    public void setSecret(String secret) {
        JWTTokenUtils.secret = secret;
    }

    private JWTTokenUtils() {
    }

    public static void validate(String token) throws IllegalArgumentException, UnsupportedEncodingException {

        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).build(); // Reusable verifier instance
        verifier.verify(token);
    }
}