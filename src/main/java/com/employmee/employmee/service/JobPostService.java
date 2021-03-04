package com.employmee.employmee.service;

import java.util.List;

import javax.validation.Valid;

import com.employmee.employmee.entity.BusinessProfile;
import com.employmee.employmee.entity.JobPost;
import com.employmee.employmee.entity.UserProfile;
import com.employmee.employmee.payload.request.CreateJobPostRequest;
import com.employmee.employmee.payload.request.UpdateJobPostDeadlineRequest;
import com.employmee.employmee.payload.request.UpdateJobPostStatusRequest;
import com.employmee.employmee.payload.response.UserJobPost;

public interface JobPostService {
	public void createJobPost(BusinessProfile businessProfile, CreateJobPostRequest createJobPostRequest);
	
	public void updateJobPost(JobPost jobPost, CreateJobPostRequest createJobPostRequest);
	
	public void updateJobPostDeadline(JobPost jobPost, UpdateJobPostDeadlineRequest updateJobPostDeadlineRequest);

	public void updateJobPostStatus(JobPost jobPost, @Valid UpdateJobPostStatusRequest updateJobPostStatusRequest);
	
	public List<UserJobPost> searchJobPosts(UserProfile userProfile, String searchField, String country, String state, String city);
	
	public UserJobPost getJobPostById(UserProfile userProfile, int jobPostId);
}
