package com.vuebackend.communication;

public class TokenRequest {
    
    public String token;

    
    public TokenRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}