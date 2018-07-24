package com.vuebackend.entitiydata;

public class DatapointData {
    private double value;
    private long time;
    private String topic;

    public DatapointData(){}

    public DatapointData(double value, long time, String topic) {
        this.value = value;
        this.time = time;
        this.topic = topic;
    }

    public long getTime() {
        return this.time;
    }

    public double getValue() {
        return this.value;
    }

    public String getTopic() {
        return this.topic;
    }
}