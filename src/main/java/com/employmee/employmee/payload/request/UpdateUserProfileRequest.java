package com.employmee.employmee.payload.request;

public class UpdateUserProfileRequest {
	
	private String bio;
	
	private String phoneNumber;
	
	private String personalWebsite;
	
	private String githubLink;
	
	public UpdateUserProfileRequest() {}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
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
	
	
	
}
