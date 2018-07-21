package com.vuebackend.entitiydata;

import java.util.ArrayList;
import java.util.List;

public class EndpointData {
    private String name;
    private String description;
    private String token;
    private List<SensorData> sensors;

    public EndpointData(String name, String description, String token, List<SensorData> sensors) {
        this.name = name;
        this.description = description;
        this.token = token;
        this.sensors = sensors;
    }

    public EndpointData(String name, String description, String token) {
        this.name = name;
        this.description = description;
        this.token = token;
        this.sensors = new ArrayList<SensorData>();
    }

	public String getName() {
		return name;
    }

    public String getDescription() {
		return description;
    }

    public String getToken() {
		return token;
    }

    public List<SensorData> getSensors() {
        return this.sensors;
    }

    public void addSensor(SensorData sensor) {
        this.sensors.add(sensor);
    }
}