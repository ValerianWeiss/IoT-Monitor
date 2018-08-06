package com.vuebackend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtTokenClaimUtils {
   
    private JwtTokenClaimUtils(){}

    public static String getUsername(String token) {
        token = token.substring(7);
        DecodedJWT jwt = JWT.decode(token);
        Claim username = jwt.getClaim("username");
        return username.asString();
    }
}