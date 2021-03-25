package com.employmee.employmee.payload.response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.employmee.employmee.entity.Address;
import com.employmee.employmee.entity.BusinessProfile;
import com.employmee.employmee.entity.Update;

public class BusinessProfileResponse {

	private String companyName;
	
	private String description;
	
	private String profileImage;
	
	private String website;
	
	private String phoneNumber;
	
	private List<Address> addresses;
	
	private List<JobPost> jobPosts;
	
	private List<Update> updates;

	public BusinessProfileResponse(BusinessProfile businessProfile, List<Update> updates) {
		this.companyName = businessProfile.getCompanyName();
		this.description = businessProfile.getDescription();
		this.profileImage = businessProfile.getProfileImage();
		this.website = businessProfile.getWebsite();
		this.phoneNumber = businessProfile.getPhoneNumber();
		ArrayList<Address> addresses = new ArrayList<>();
		addresses.addAll(businessProfile.getAddresses());
		this.addresses = addresses;
		this.jobPosts = businessProfile.getJobPosts().stream()
				                                     .map(jobPost -> new JobPost(jobPost))
				                                     .collect(Collectors.toList());
		this.updates = updates;
	}
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public List<JobPost> getJobPosts() {
		return jobPosts;
	}

	public void setJobPosts(List<JobPost> jobPosts) {
		this.jobPosts = jobPosts;
	}

	public List<Update> getUpdates() {
		return updates;
	}

	public void setUpdates(List<Update> updates) {
		this.updates = updates;
	}
	
	
}
