package com.employmee.employmee.payload.response;

public class Bookmark {
	
	private int jobPostId;
	
	private String jobPostTitle;
	
	private int companyId;
	
	private String companyName;
	
	public Bookmark(int jobPostId, String jobPostTitle, int companyId, String companyName) {
		this.jobPostId = jobPostId;
		this.jobPostTitle = jobPostTitle;
		this.companyId = companyId;
		this.companyName = companyName;
	}
	
	public int getJobPostId() {
		return jobPostId;
	}
	public void setJobPostId(int jobPostId) {
		this.jobPostId = jobPostId;
	}
	public String getJobPostTitle() {
		return jobPostTitle;
	}
	public void setJobPostTitle(String jobPostTitle) {
		this.jobPostTitle = jobPostTitle;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	
}
