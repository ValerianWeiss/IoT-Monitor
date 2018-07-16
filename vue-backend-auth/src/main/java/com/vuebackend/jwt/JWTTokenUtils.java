package com.vuebackend.jwt;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.vuebackend.communication.CreateTokenRequest;

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
    public static String create(CreateTokenRequest tokenRequest, boolean canExpire)
        throws IllegalArgumentException, UnsupportedEncodingException {

        Algorithm algorithm = Algorithm.HMAC256(secret);
        final long createdAtTime = System.currentTimeMillis();
        Builder tokenBuilder = JWT.create().withIssuedAt(new Date(createdAtTime));

        if(canExpire) {
            final long expirationTime = createdAtTime + periodOfValidity;
            tokenBuilder.withExpiresAt(new Date(expirationTime));
        }

        addClaims(tokenBuilder, tokenRequest);
        String token = tokenBuilder.sign(algorithm);
        // Own claims can be inserted if needed
        System.out.println("token: " + token);
        return token;
    }

    public static DecodedJWT verify(String token) throws IllegalArgumentException, UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).build(); // Reusable verifier instance
        return verifier.verify(token);        
    }

    private static void addClaims(Builder tokenBuilder, CreateTokenRequest request) {
        
        Map<String, String> stringClaims = request.getClaims(String.class);
        if(stringClaims != null) {
            for (Entry<String, String> entry : stringClaims.entrySet()) {
                tokenBuilder.withClaim(entry.getKey(), entry.getValue());
            }
        }
        
        Map<String, Integer> IntegerClaims = request.getClaims(Integer.class);
        if(IntegerClaims != null) {
            for (Entry<String, Integer> entry : IntegerClaims.entrySet()) {
                tokenBuilder.withClaim(entry.getKey(), entry.getValue());
            }
        }
    }
}