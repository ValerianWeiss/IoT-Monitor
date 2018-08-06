package com.vuebackend.communication;

import com.vuebackend.entitiydata.DatapointData;

public class AddDatapointRequest {
    
    private String endpointName;
    private String sensorName;
    private DatapointData datapoint;


    public AddDatapointRequest(){}

	public AddDatapointRequest(String endpointName, DatapointData dataPoint) {
        this.endpointName = endpointName;
        this.datapoint = dataPoint;
    }

    public String getEndpointName() {
        return this.endpointName;
    }

    public DatapointData getDatapoint() {
        return this.datapoint;
    }

	public String getSensorName() {
		return sensorName;
	}

	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}
}