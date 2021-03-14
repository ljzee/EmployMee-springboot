package com.employmee.employmee.payload.request;

import java.util.List;

import javax.validation.constraints.NotNull;

public class SubmitApplicationRequest {
	@NotNull(message = "Job post ID must not be empty.")
	private Integer jobPostId;
	
	private List<Integer> documentIds;

	public SubmitApplicationRequest() {}
	
	public int getJobPostId() {
		return jobPostId;
	}

	public void setJobPostId(int jobPostId) {
		this.jobPostId = jobPostId;
	}

	public List<Integer> getDocumentIds() {
		return documentIds;
	}

	public void setDocumentIds(List<Integer> documentIds) {
		this.documentIds = documentIds;
	}
	
	
}
