package com.vuebackend.communication;

public class DataPoint{

    private double value;
    private long time;


    public DataPoint(double value) {
        this.value = value;
        this.time = System.currentTimeMillis();
    }

	public double getValue() {
		return value;
	}

	public long getTime() {
		return time;
	}
}