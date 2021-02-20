package com.employmee.employmee.payload.response;


import java.util.Iterator;
import java.util.Set;

import com.employmee.employmee.entity.Address;
import com.employmee.employmee.entity.JobPost;

public class BusinessJobPost {
	
	private int id;
	
	private String title;
	
	private String city;
	
	private String state;

	private String deadline;
	
	private Integer applicantCount;
	
	private String status;
	
	public BusinessJobPost(int id, String title, String city, String state) {
		this.id = id;
		this.title = title;
		this.city = city;
		this.state = state;
	}

	public BusinessJobPost(JobPost jobPost) {
		this.id = jobPost.getId();
		this.title = jobPost.getTitle();
		this.deadline = jobPost.getDeadline() != null ? jobPost.getDeadline().toString() : null;
		this.applicantCount = 0;
		this.status= jobPost.getStatus().name();
		
		Set<Address> addresses = jobPost.getAddresses();
		Iterator<Address> addressesIterator = addresses.iterator();
		if(addressesIterator.hasNext()); {
			Address address = addressesIterator.next();
			this.city = address.getCity();
			this.state = address.getCountry();
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public Integer getApplicantCount() {
		return applicantCount;
	}

	public void setApplicantCount(Integer applicantCount) {
		this.applicantCount = applicantCount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}
