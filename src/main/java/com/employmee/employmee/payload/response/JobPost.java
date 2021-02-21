package com.employmee.employmee.payload.response;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.employmee.employmee.entity.Address;

public class JobPost {
	private int id;
	
	private String title;
	
	private String duration;
	
	private String positionType;
	
	private int openings;
	
	private String description;
	
	private String salary;
	
	private String deadline;
	
	private boolean coverletterRequired;
	
	private boolean resumeRequired;
	
	private boolean otherRequired;
	
	private String datePublished;
	
	private List<Address> jobAddresses;
	
	private String companyName;
	
	private String companyPhoneNumber;
	
	private String companyWebsite;
	
	public JobPost(com.employmee.employmee.entity.JobPost jobPost) {
		this.id = jobPost.getId();
		this.title = jobPost.getTitle();
		this.duration = jobPost.getDuration();
		this.positionType = jobPost.getPositionType().name();
		this.openings = jobPost.getOpenings();
		this.description = jobPost.getDescription();
		this.salary = jobPost.getSalary();
		this.deadline = jobPost.getDeadline() != null ? jobPost.getDeadline().toString() : null;
		this.coverletterRequired = jobPost.isCoverletterRequired();
		this.resumeRequired = jobPost.isResumeRequired();
		this.otherRequired = jobPost.isOtherRequired();
		this.datePublished = jobPost.getDatePublished() != null ? jobPost.getDatePublished().toString() : null;
		this.companyName = jobPost.getBusinessProfile().getCompanyName();
		this.companyPhoneNumber = jobPost.getBusinessProfile().getPhoneNumber();
		this.companyWebsite = jobPost.getBusinessProfile().getWebsite();
		this.jobAddresses = new ArrayList<Address>(jobPost.getAddresses());
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

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getPositionType() {
		return positionType;
	}

	public void setPositionType(String positionType) {
		this.positionType = positionType;
	}

	public int getOpenings() {
		return openings;
	}

	public void setOpenings(int openings) {
		this.openings = openings;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public boolean isCoverletterRequired() {
		return coverletterRequired;
	}

	public void setCoverletterRequired(boolean coverletterRequired) {
		this.coverletterRequired = coverletterRequired;
	}

	public boolean isResumeRequired() {
		return resumeRequired;
	}

	public void setResumeRequired(boolean resumeRequired) {
		this.resumeRequired = resumeRequired;
	}

	public boolean isOtherRequired() {
		return otherRequired;
	}

	public void setOtherRequired(boolean otherRequired) {
		this.otherRequired = otherRequired;
	}

	public String getDatePublished() {
		return datePublished;
	}

	public void setDatePublished(String datePublished) {
		this.datePublished = datePublished;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyPhoneNumber() {
		return companyPhoneNumber;
	}

	public void setCompanyPhoneNumber(String companyPhoneNumber) {
		this.companyPhoneNumber = companyPhoneNumber;
	}

	public String getCompanyWebsite() {
		return companyWebsite;
	}

	public void setCompanyWebsite(String companyWebsite) {
		this.companyWebsite = companyWebsite;
	}

	public List<Address> getJobAddresses() {
		return jobAddresses;
	}

	public void setJobAddresses(List<Address> jobAddresses) {
		this.jobAddresses = jobAddresses;
	}
	
	
}
