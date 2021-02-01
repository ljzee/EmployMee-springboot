package com.employmee.employmee.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.employmee.employmee.annotation.ValueOfEnum;
import com.employmee.employmee.entity.User;

public class RegisterRequest {
	
	@Size(min = 8, message = "Username must have a minimum of 8 characters.")
	private String username;
	
	@NotBlank(message = "Email must not be blank.")
	@Email(message = "Email must be the form jobs@jobs.com.")
	private String email;
	
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "Minimum 8 characters, at least one uppercase letter, one lowercase letter and one number.")
	private String password;
	
	@ValueOfEnum(enumClass = User.ROLE.class, message="User type must be one of USER or BUSINESS.")
	private String userType;
	
	public RegisterRequest() {}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Override
	public String toString() {
		return "RegisterRequest [username=" + username + ", email=" + email + ", password=" + password + ", userType="
				+ userType + "]";
	}
	
	
}
