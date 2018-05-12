package com.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "session")
public class Session {

    @Id
    @GeneratedValue
    private Long id;
    private String sessionToken;

    @NotNull
    @OneToOne(optional = false)
    private User user;

    public Session(){}

    public Session(User user, String sessionToken) {
        this.user = user;
        this.sessionToken = sessionToken;
    }

    public String getSessionToken() {
		return sessionToken;
	}

    public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
    }
    
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}