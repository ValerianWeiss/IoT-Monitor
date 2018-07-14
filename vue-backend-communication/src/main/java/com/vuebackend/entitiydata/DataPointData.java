package com.vuebackend.entitiydata;

public class DataPointData {
    private double value;
    private long time;

    public DataPointData(){}

    public DataPointData(double value, long time) {
        this.value = value;
        this.time = time;
    }

    public long getTime() {
        return this.time;
    }

    public double getValue() {
        return this.value;
    }
}