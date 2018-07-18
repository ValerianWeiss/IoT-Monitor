package com.vuebackend.communication;

import com.vuebackend.entitiydata.DataPointData;

public class AddDatapointRequest {
    private String username;
    private String endpointName;
    private DataPointData datapoint;


    public AddDatapointRequest(){}

    public AddDatapointRequest(String endpointName, DataPointData dataPoint) {
        this.endpointName = endpointName;
        this.datapoint = dataPoint;
    }

    public String getEndpointName() {
        return this.endpointName;
    }

    public DataPointData getDatapoint() {
        return this.datapoint;
    }

    public String getUsername() {
        return this.username;
    }
}