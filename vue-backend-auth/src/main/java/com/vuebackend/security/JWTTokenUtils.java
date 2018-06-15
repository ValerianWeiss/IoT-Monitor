package com.vuebackend.security;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.impl.JWTParser;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTTokenUtils {

    private static String secret;
    private static long periodOfValidity;

    @Value("${HMAC256Secret}")
    public void setSecret(String secret) {
        JWTTokenUtils.secret = secret;
    }

    @Value("${TokenPeriodOfValidity}")
    public void setSperiodOfValidity(long periodOfValidity) {
        JWTTokenUtils.periodOfValidity = periodOfValidity;
    }

    private JWTTokenUtils() {}

    // Subject should be the username
    public static String create(String subject) throws IllegalArgumentException, UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        final long createdAtTime = System.currentTimeMillis();
        final long expirationTime = createdAtTime + periodOfValidity;

        // Own claims can be inserted if needed
        String token = JWT.create().withSubject(subject).withIssuedAt(new Date(createdAtTime))
                .withExpiresAt(new Date(expirationTime)).sign(algorithm);
        System.out.println("token: " + token);
        return token;
    }

    public static DecodedJWT verify(String token) throws IllegalArgumentException, UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).build(); // Reusable verifier instance
        return verifier.verify(token);        
    }
}