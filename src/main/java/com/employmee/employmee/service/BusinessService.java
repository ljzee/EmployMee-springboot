package com.employmee.employmee.service;

import com.employmee.employmee.entity.BusinessProfile;
import com.employmee.employmee.payload.request.AddUpdateRequest;
import com.employmee.employmee.payload.request.CreateBusinessProfileRequest;
import com.employmee.employmee.payload.request.UpdateBusinessProfileRequest;
import com.employmee.employmee.payload.request.UpdateProfileImageRequest;

public interface BusinessService {
	public void createProfile(int userId, CreateBusinessProfileRequest createBusinessProfileRequest);

	public void updateProfile(BusinessProfile businessProfile, UpdateBusinessProfileRequest updateBusinessProfileRequest);

	public void addUpdate(BusinessProfile businessProfile, AddUpdateRequest addUpdateRequest);

	public void updateProfileImage(BusinessProfile businessProfile, UpdateProfileImageRequest updateProfileImageRequest);
}
