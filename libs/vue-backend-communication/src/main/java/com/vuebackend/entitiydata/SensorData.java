package com.vuebackend.entitiydata;

public class SensorData {
    private String name;
    private String topic;

    public SensorData(String name, String topic) {
        this.name = name;
        this.topic = topic;
    }

	public String getName() {
		return name;
    }

    public String getTopic() {
		return topic;
    }
}