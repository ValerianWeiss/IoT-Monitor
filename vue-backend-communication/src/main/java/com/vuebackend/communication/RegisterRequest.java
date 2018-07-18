package com.vuebackend.communication;

public class RegisterRequest {

    private String username;
	private String password;
	private String passwordRepeated;

	
	public RegisterRequest() {}

	public RegisterRequest(String username, String password, 
							String passwordRepeated) {
        this.username = username;
		this.password = password;
		this.passwordRepeated = passwordRepeated;
    }

    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordRepeated() {
		return passwordRepeated;
	}

	public void setPasswordRepeated(String passwordRepeated) {
		this.passwordRepeated = passwordRepeated;
	}

	public boolean passwordsNotEqual() {
		return !this.password.equals(this.passwordRepeated);
	}
}