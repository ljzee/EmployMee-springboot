package com.employmee.employmee.service;

import com.employmee.employmee.payload.request.CreateUserProfileRequest;

public interface UserService {
	public void createProfile(int userId, CreateUserProfileRequest createUserProfileRequest);
}
