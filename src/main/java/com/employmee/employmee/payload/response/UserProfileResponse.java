package com.employmee.employmee.payload.response;

import java.util.Set;

import com.employmee.employmee.entity.Experience;
import com.employmee.employmee.entity.UserProfile;

public class UserProfileResponse {
	
	private String firstName;
	
	private String lastName;
	
	private String bio;
	
	private String email;
	
	private String phoneNumber;
	
	private String personalWebsite;
	
	private String githubLink;
	
	private String profileImage;
	
	private Set<Experience> experiences;

	public UserProfileResponse(UserProfile userProfile) {
		this.firstName = userProfile.getFirstName();
		this.lastName = userProfile.getLastName();
		this.bio = userProfile.getBio();
		this.email = userProfile.getUser().getEmail();
		this.phoneNumber = userProfile.getPhoneNumber();
		this.personalWebsite = userProfile.getPersonalWebsite();
		this.githubLink = userProfile.getGithubLink();
		this.profileImage = userProfile.getProfileImage();
		this.experiences = userProfile.getExperiences();
	}
		
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

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Set<Experience> getExperiences() {
		return experiences;
	}

	public void setExperiences(Set<Experience> experiences) {
		this.experiences = experiences;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}
	
	
	
}
