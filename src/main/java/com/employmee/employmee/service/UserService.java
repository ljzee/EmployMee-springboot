package com.employmee.employmee.service;

import java.nio.file.Path;

import org.springframework.web.multipart.MultipartFile;

import com.employmee.employmee.entity.JobPost;
import com.employmee.employmee.entity.UserProfile;
import com.employmee.employmee.payload.request.AddExperienceRequest;
import com.employmee.employmee.payload.request.CreateUserProfileRequest;
import com.employmee.employmee.payload.request.UpdateUserProfileRequest;

public interface UserService {
	public void createProfile(int userId, CreateUserProfileRequest createUserProfileRequest);
	
	public void updateProfile(UserProfile userProfile, UpdateUserProfileRequest updateUserProfileRequest);
	
	public void toggleBookmarkJobPost(UserProfile userProfile, JobPost jobPost); 
	
	public void addDocument(UserProfile userProfile, Path path, MultipartFile file, String type, String name);
	
	public void addExperience(UserProfile userProfile, AddExperienceRequest addExperienceRequest);
}
