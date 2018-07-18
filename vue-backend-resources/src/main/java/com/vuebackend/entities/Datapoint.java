package com.vuebackend.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

@Entity
@Table(name = "datapoint")
public class Datapoint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private double value;
    private long time;
  
    @NonNull
    @ManyToOne
    private Sensor sensor;

    
    public Datapoint(){}

    public Datapoint(Sensor sensor, double value, long time) {
        this.sensor = sensor;
        this.value = value;
        this.time = time;
    }

    public long getTime() {
		return time;
	}

    public double getValue() {
		return value;
	}

    public void setValue(double value) {
		this.value = value;
	}

    public void setTime(long time) {
		this.time = time;
    }
    
    public Sensor getSensor() {
        return this.sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}