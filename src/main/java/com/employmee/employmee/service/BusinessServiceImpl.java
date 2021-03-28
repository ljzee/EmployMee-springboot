package com.employmee.employmee.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employmee.employmee.entity.Address;
import com.employmee.employmee.entity.BusinessProfile;
import com.employmee.employmee.entity.Update;
import com.employmee.employmee.entity.User;
import com.employmee.employmee.exception.ProfileAlreadyCreatedException;
import com.employmee.employmee.exception.UserNotFoundException;
import com.employmee.employmee.payload.request.AddUpdateRequest;
import com.employmee.employmee.payload.request.CreateBusinessProfileRequest;
import com.employmee.employmee.payload.request.UpdateBusinessProfileRequest;
import com.employmee.employmee.repository.BusinessProfileRepository;
import com.employmee.employmee.repository.UpdateRepository;
import com.employmee.employmee.repository.UserRepository;

@Service
public class BusinessServiceImpl implements BusinessService {

	@Autowired
	BusinessProfileRepository businessProfileRepository;
	
	@Autowired
	UserRepository userRepostiory;
	
	@Autowired
	UpdateRepository updateRepository;
	
	@Override
	public void createProfile(int userId, CreateBusinessProfileRequest createBusinessProfileRequest) {
		User user = userRepostiory.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
		
		if(businessProfileRepository.existsById(userId)) {
			throw new ProfileAlreadyCreatedException(userId);
		}
		
		BusinessProfile newBusinessProfile = new BusinessProfile();
		newBusinessProfile.setUser(user);
		newBusinessProfile.setCompanyName(createBusinessProfileRequest.getCompanyName());
		newBusinessProfile.setPhoneNumber(createBusinessProfileRequest.getPhoneNumber());
		newBusinessProfile.setWebsite(createBusinessProfileRequest.getWebsite());
		newBusinessProfile.setDescription(createBusinessProfileRequest.getDescription());
		
		Address address = new Address();
		address.setStreetNameNo(createBusinessProfileRequest.getStreetAddress());
		address.setCity(createBusinessProfileRequest.getCity());
		address.setState(createBusinessProfileRequest.getState());
		address.setCountry(createBusinessProfileRequest.getCountry());
		address.setPostalCode(createBusinessProfileRequest.getPostalCode());
		
		Set<Address> addresses = new HashSet<>();
		addresses.add(address);
		newBusinessProfile.setAddresses(addresses);
		
		businessProfileRepository.save(newBusinessProfile);
	}

	@Override
	public void updateProfile(BusinessProfile businessProfile,
			UpdateBusinessProfileRequest updateBusinessProfileRequest) {
		businessProfile.setDescription(updateBusinessProfileRequest.getDescription());
		businessProfile.setWebsite(updateBusinessProfileRequest.getWebsite());
		businessProfile.setPhoneNumber(updateBusinessProfileRequest.getPhoneNumber());
		
		businessProfileRepository.save(businessProfile);
	}

	@Override
	public void addUpdate(BusinessProfile businessProfile, AddUpdateRequest addUpdateRequest) {
		Update update = new Update();
		update.setContent(addUpdateRequest.getContent());
		update.setBusinessProfile(businessProfile);
		update.setDatePosted(LocalDateTime.now());
		
		updateRepository.save(update);
	}

}
