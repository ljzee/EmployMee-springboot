package com.employmee.employmee.service;

import javax.validation.Valid;

import com.employmee.employmee.entity.BusinessProfile;
import com.employmee.employmee.entity.JobPost;
import com.employmee.employmee.payload.request.CreateJobPostRequest;
import com.employmee.employmee.payload.request.UpdateJobPostDeadlineRequest;
import com.employmee.employmee.payload.request.UpdateJobPostStatusRequest;

public interface JobPostService {
	public void createJobPost(BusinessProfile businessProfile, CreateJobPostRequest createJobPostRequest);
	
	public void updateJobPost(JobPost jobPost, CreateJobPostRequest createJobPostRequest);
	
	public void updateJobPostDeadline(JobPost jobPost, UpdateJobPostDeadlineRequest updateJobPostDeadlineRequest);

	public void updateJobPostStatus(JobPost jobPost, @Valid UpdateJobPostStatusRequest updateJobPostStatusRequest);
}
