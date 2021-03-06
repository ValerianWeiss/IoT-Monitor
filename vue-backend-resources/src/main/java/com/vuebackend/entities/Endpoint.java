package com.vuebackend.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
    private String token;
   
    @NonNull
    @ManyToOne
    private User user;


    public Endpoint(){}

    public Endpoint(User user, String name, String token) {
        this(user, name, token, null);
    }

    public Endpoint(User user, String name, String token, String description) {
        this.user = user;
        this.name = name;
        this.description = description;
        this.token = token;
    }

    public long getId() {
        return this.id;
    }
    
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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