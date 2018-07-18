package com.vuebackend.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "sensor")
public class Sensor {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String name;
    
    @ManyToOne
    private Endpoint endpoint;
    
    @OneToMany
    private Set<Datapoint> datapoints;


    public Sensor(){}

	public Sensor(String name, Endpoint endpoint) {
        this.name = name;
        this.endpoint = endpoint;
    }
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
    }
    
    public Endpoint getEndpoint() {
        return this.endpoint;
    }

    public void addDatapoint(Datapoint datapoint) {
        this.datapoints.add(datapoint);
    }

    public Set<Datapoint> getDatapoints() {
        return this.datapoints;
    }
}