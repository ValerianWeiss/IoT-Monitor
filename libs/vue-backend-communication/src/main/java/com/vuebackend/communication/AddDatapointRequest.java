package com.vuebackend.communication;

import com.vuebackend.entitiydata.DatapointData;

public class AddDatapointRequest {
    
    private String sensorName;
    private DatapointData datapoint;


    public AddDatapointRequest(){}

	public AddDatapointRequest(String sensorName, DatapointData dataPoint) {
        this.sensorName = sensorName;
        this.datapoint = dataPoint;
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