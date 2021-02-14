package com.employmee.employmee.service;

import com.employmee.employmee.payload.request.CreateBusinessProfileRequest;

public interface BusinessService {
	public void createProfile(int userId, CreateBusinessProfileRequest createBusinessProfileRequest);
}
