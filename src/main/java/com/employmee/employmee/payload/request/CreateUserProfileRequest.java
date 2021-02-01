package com.employmee.employmee.payload.request;

import javax.validation.constraints.NotBlank;

public class CreateUserProfileRequest {
	
	@NotBlank(message = "First name must not be blank.")
	private String firstName;
	
	@NotBlank(message = "Last name must not be blank.")
	private String lastName;
	
	private String phoneNumber;
	
	private String personalWebsite;
	
	private String githubLink;
	
	private String bio;
	
	public CreateUserProfileRequest() {}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPersonalWebsite() {
		return personalWebsite;
	}

	public void setPersonalWebsite(String personalWebsite) {
		this.personalWebsite = personalWebsite;
	}

	public String getGithubLink() {
		return githubLink;
	}

	public void setGithubLink(String githubLink) {
		this.githubLink = githubLink;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}
	
	
}
