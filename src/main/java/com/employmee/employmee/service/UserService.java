package com.employmee.employmee.service;

import com.employmee.employmee.entity.JobPost;
import com.employmee.employmee.entity.UserProfile;
import com.employmee.employmee.payload.request.CreateUserProfileRequest;

public interface UserService {
	public void createProfile(int userId, CreateUserProfileRequest createUserProfileRequest);
	
	public void toggleBookmarkJobPost(UserProfile userProfile, JobPost jobPost); 
}
