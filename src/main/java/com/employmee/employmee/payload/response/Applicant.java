package com.employmee.employmee.payload.response;

import com.employmee.employmee.entity.Application;

public class Applicant {

	private int applicationId;
	
	private String dateProcessed;
	
	private int applicantId;
	
	private String firstName;
	
	private String lastName;
	
	private String phoneNumber;
	
	private String email;
	
	private String status;
	
	private String profileImage;
	
	public Applicant(Application application) {
		this.applicationId = application.getId();
		this.dateProcessed = application.getDateProcessed() != null ? application.getDateProcessed().toString() : null;
		this.applicantId = application.getUserProfile().getId();
		this.firstName = application.getUserProfile().getFirstName();
		this.lastName = application.getUserProfile().getLastName();
		this.phoneNumber = application.getUserProfile().getPhoneNumber();
		this.email = application.getUserProfile().getUser().getEmail();
		this.status = application.getStatus().name();
		this.profileImage = application.getUserProfile().getProfileImage();
	}

	public int getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}

	public String getDateProcessed() {
		return dateProcessed;
	}

	public void setDateProcessed(String dateProcessed) {
		this.dateProcessed = dateProcessed;
	}

	public int getApplicantId() {
		return applicantId;
	}

	public void setApplicantId(int applicantId) {
		this.applicantId = applicantId;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	
}
