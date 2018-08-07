package com.vuebackend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtTokenClaimUtils {
   
    private JwtTokenClaimUtils(){}

    public static String getUsername(String token) {
        return getStringClaim(token, "username");
    }

    public static String getEndpointname(String token) {
        return getStringClaim(token, "endpointName");
    }

    private static String getStringClaim(String token, String claim) {
        token = token.substring(7);
        DecodedJWT jwt = JWT.decode(token);
        Claim username = jwt.getClaim(claim);
        return username.asString();
    }
}