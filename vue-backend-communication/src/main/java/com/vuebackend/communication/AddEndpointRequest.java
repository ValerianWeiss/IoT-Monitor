package com.vuebackend.communication;

public class AddEndpointRequest {
    private String name;
    private String description;
    private String username;


    public AddEndpointRequest(){}

    public AddEndpointRequest(String username, String name) {
        this(username, name, null);
    }

    public AddEndpointRequest(String username, String name, String description) {
        this.username = username;
        this.name = name;
        this.description = description;
    }

    public String getUsername() {
        return this.username;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }
}