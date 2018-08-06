package com.vuebackend.communication;

public class AddEndpointRequest {
    private String endpointName;
    private String description;
    private String[] sensorNames;


    public AddEndpointRequest(){}	

	public AddEndpointRequest(String endpointName, String[] sensorNames) {
        this(endpointName, null, sensorNames);
    }

    public AddEndpointRequest(String endpointName, String description, String[] sensorNames) {
        this.endpointName = endpointName;
        this.description = description;
        this.sensorNames = sensorNames;
    }

    public String getEndpointName() {
        return this.endpointName;
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