package com.employmee.employmee.payload.response;

import com.employmee.employmee.entity.Application;

public class UserApplication {
	private int id;
	
	private String status;
	
	private String dateSubmitted;
	
	private String dateProcessed;
	
	private String title;
	
	private int jobPostId;
	
	private String companyName;
	
	private int companyId;
	
	public UserApplication(Application application) {
		this.id = application.getId();
		this.status = application.getStatus().name();
		this.dateSubmitted = application.getDateSubmitted().toString();
		this.dateProcessed = application.getDateProcessed() != null ? application.getDateProcessed().toString() : null;
		this.title = application.getJobPost().getTitle();
		this.jobPostId  = application.getJobPost().getId();
		this.companyName = application.getJobPost().getBusinessProfile().getCompanyName();
		this.companyId = application.getJobPost().getBusinessProfile().getId();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDateSubmitted() {
		return dateSubmitted;
	}

	public void setDateSubmitted(String dateSubmitted) {
		this.dateSubmitted = dateSubmitted;
	}

	public String getDateProcessed() {
		return dateProcessed;
	}

	public void setDateProcessed(String dateProcessed) {
		this.dateProcessed = dateProcessed;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getJobPostId() {
		return jobPostId;
	}

	public void setJobPostId(int jobPostId) {
		this.jobPostId = jobPostId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	
	
}
