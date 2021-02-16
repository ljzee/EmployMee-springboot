package com.employmee.employmee.service;

import com.employmee.employmee.entity.BusinessProfile;
import com.employmee.employmee.payload.request.CreateJobPostRequest;

public interface JobPostService {
	public void createJobPost(BusinessProfile businessProfile, CreateJobPostRequest createJobPostRequest);
}
