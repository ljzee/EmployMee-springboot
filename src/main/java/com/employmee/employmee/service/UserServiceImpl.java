package com.employmee.employmee.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employmee.employmee.entity.User;
import com.employmee.employmee.entity.UserProfile;
import com.employmee.employmee.exception.UserNotFoundException;
import com.employmee.employmee.exception.ProfileAlreadyCreatedException;
import com.employmee.employmee.payload.request.CreateUserProfileRequest;
import com.employmee.employmee.repository.UserProfileRepository;
import com.employmee.employmee.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserProfileRepository userProfileRepository;
	
	@Autowired
	UserRepository userRepostiory;
	
	@Override
	public void createProfile(int userId, CreateUserProfileRequest createUserProfileRequest) {
		User user = userRepostiory.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
		
		if(userProfileRepository.existsById(userId)) {
			throw new ProfileAlreadyCreatedException(userId);
		}
		
		UserProfile newUserProfile = new UserProfile();
		newUserProfile.setUser(user);
		newUserProfile.setFirstName(createUserProfileRequest.getFirstName());
		newUserProfile.setLastName(createUserProfileRequest.getLastName());
		newUserProfile.setPhoneNumber(createUserProfileRequest.getPhoneNumber());
		newUserProfile.setPersonalWebsite(createUserProfileRequest.getPersonalWebsite());
		newUserProfile.setGithubLink(createUserProfileRequest.getGithubLink());
		newUserProfile.setBio(createUserProfileRequest.getBio());
		
		userProfileRepository.save(newUserProfile);
	}

}
