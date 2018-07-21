package com.vuebackend.communication;

import java.util.ArrayList;
import java.util.List;

import com.vuebackend.entitiydata.EndpointData;

public class GetAllEndpointsResponse {
    private List<EndpointData> endpoints;

    public GetAllEndpointsResponse(List<EndpointData> endpoints) {
        this.endpoints = endpoints;
    }

    public GetAllEndpointsResponse() {
        this.endpoints = new ArrayList<EndpointData>();
    }

    public void addEndpoint(EndpointData endpoint) {
        this.endpoints.add(endpoint);
    }

    public List<EndpointData> getEndpoints() {
        return this.endpoints;
    }
}