package com.vuebackend.communication;

public class RegisterRequest {

    private String username;
	private String password;
	private String passwordRepeated;
    private String email;

	public RegisterRequest(String username, String password, 
						   String passwordRepeated, String email) {
        this.username = username;
		this.password = password;
		this.passwordRepeated = passwordRepeated;
		this.email = email;
    }

    public String getEmail() {
		return email;
	}

    public void setEmail(String email) {
		this.email = email;
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