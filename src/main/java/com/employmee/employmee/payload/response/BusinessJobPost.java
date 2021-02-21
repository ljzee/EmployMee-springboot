package com.employmee.employmee.payload.response;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.employmee.employmee.entity.Address;

public class BusinessJobPost extends JobPost {
	
	private String status;
	
	private String dateCreated;

	private int applicationCount;
	
	private List<Address> businessAddresses;
	
	public BusinessJobPost(com.employmee.employmee.entity.JobPost jobPost) {
		super(jobPost);
		
		this.status= jobPost.getStatus().name();
		this.dateCreated = jobPost.getDateCreated().toString();
		this.applicationCount = 0;
		this.businessAddresses = new ArrayList<Address>(jobPost.getBusinessProfile().getAddresses());
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public int getApplicationCount() {
		return applicationCount;
	}

	public void setApplicationCount(int applicationCount) {
		this.applicationCount = applicationCount;
	}

	public List<Address> getBusinessAddresses() {
		return businessAddresses;
	}

	public void setBusinessAddresses(List<Address> businessAddresses) {
		this.businessAddresses = businessAddresses;
	}
	
	
}
