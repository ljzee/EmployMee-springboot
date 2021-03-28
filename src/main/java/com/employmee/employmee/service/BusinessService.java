package com.employmee.employmee.service;

import com.employmee.employmee.entity.BusinessProfile;
import com.employmee.employmee.payload.request.AddUpdateRequest;
import com.employmee.employmee.payload.request.CreateBusinessProfileRequest;
import com.employmee.employmee.payload.request.UpdateBusinessProfileRequest;

public interface BusinessService {
	public void createProfile(int userId, CreateBusinessProfileRequest createBusinessProfileRequest);

	public void updateProfile(BusinessProfile businessProfile, UpdateBusinessProfileRequest updateBusinessProfileRequest);

	public void addUpdate(BusinessProfile businessProfile, AddUpdateRequest addUpdateRequest);
}
