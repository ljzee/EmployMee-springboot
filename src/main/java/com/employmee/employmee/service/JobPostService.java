package com.employmee.employmee.service;

import com.employmee.employmee.entity.BusinessProfile;
import com.employmee.employmee.entity.JobPost;
import com.employmee.employmee.payload.request.CreateJobPostRequest;
import com.employmee.employmee.payload.request.UpdateJobPostDeadlineRequest;

public interface JobPostService {
	public void createJobPost(BusinessProfile businessProfile, CreateJobPostRequest createJobPostRequest);
	
	public void updateJobPostDeadline(JobPost jobPost, UpdateJobPostDeadlineRequest updateJobPostDeadlineRequest);
}
