package com.vuebackend.communication;

public class AddEndpointRequest {
    private String name;
    private String description;
    private String username;
    private String[] sensorNames;


    public AddEndpointRequest(){}	

	public AddEndpointRequest(String username, String name, String[] sensorNames) {
        this(username, name, null, sensorNames);
    }

    public AddEndpointRequest(String username, String name, String description, String[] sensorNames) {
        this.username = username;
        this.name = name;
        this.description = description;
        this.sensorNames = sensorNames;
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
    
    public String[] getSensorNames() {
		return sensorNames;
	}

	public void setSensorNames(String[] sensorName) {
		this.sensorNames = sensorName;
	}
}