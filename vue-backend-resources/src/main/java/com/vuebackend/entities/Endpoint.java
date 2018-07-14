package com.vuebackend.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

@Entity
@Table(name = "endpoint")
public class Endpoint {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String name;
    private String description;
    @OneToMany
    private Set<Datapoint> datapoint;
    
    @NonNull
    @ManyToOne
    private User user;


    public Endpoint(User user, String name) {
        this.user = user;
        this.name = name;
        this.description = null;
    }
    
    public Endpoint(User user, String name, String description) {
        this.user = user;
        this.name = name;
        this.description = description;
    }

    public String getName() {
		return name;
	}

    public String getDescription() {
		return description;
	}

    public void setDescription(String description) {
		this.description = description;
	}

    public void setName(String name) {
		this.name = name;
	}
}