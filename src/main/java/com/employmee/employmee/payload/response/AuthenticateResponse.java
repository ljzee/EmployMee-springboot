package com.employmee.employmee.payload.response;

public class AuthenticateResponse {
	private int id;
	private String username;
	private String email;
	private String role;
	private String token;
	private boolean hasProfile;
	
	public AuthenticateResponse(int id, String username, String email, String role, String token, boolean hasProfile) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.role = role;
		this.token = token;
		this.hasProfile = hasProfile;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}

	public boolean isHasProfile() {
		return hasProfile;
	}

	public void setHasProfile(boolean hasProfile) {
		this.hasProfile = hasProfile;
	}
	
	
}
