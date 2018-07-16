package com.vuebackend.communication;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class CreateTokenRequest {
    
    private List<Map<String,?>> claimList = new ArrayList<Map<String,?>>();


    public <T> Map<String, T> getClaims(Class<T> type) {
        int index = checkIfMapExists(type);
        if(index >= 0) {
            return (Map<String, T>)claimList.get(index);
        } else {
            return null;
        }
    }

    public CreateTokenRequest addIntegerClaims(Map<String, Integer> claims) {
        int index = checkIfMapExists(Integer.class);
        addClaims(claims, index);
        return this;
    }

    public CreateTokenRequest addStringClaims(Map<String, String> claims) {
        int index = checkIfMapExists(String.class);
        addClaims(claims, index);
        return this;
    }

    private <T> void addClaims(Map<String, T> claims, int index) {
        if(index >= 0) {
            Map<String, T> map = (Map<String, T>)this.claimList.get(index);
            map.putAll(claims);
        } else {
            this.claimList.add(claims);
        }
    }

    private int checkIfMapExists(Class<?> type) {
        for (int i = 0; i < this.claimList.size(); i++) {
            Map<String, ?> map = this.claimList.get(i);

            if(map.values().iterator().next().getClass().equals(type)) {
                return i;
            }
        }
        return -1;
    }
}