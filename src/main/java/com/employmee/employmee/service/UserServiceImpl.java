package com.employmee.employmee.service;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.employmee.employmee.entity.Document;
import com.employmee.employmee.entity.JobPost;
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

	@Override
	public void toggleBookmarkJobPost(UserProfile userProfile, JobPost jobPost) {
		Set<JobPost> bookmarkedJobPosts = userProfile.getBookmarkedJobPosts();
		if(bookmarkedJobPosts.contains(jobPost)) {
			bookmarkedJobPosts.remove(jobPost);
		} else {
			bookmarkedJobPosts.add(jobPost);
		}
		
		userProfileRepository.save(userProfile);
	}

	@Override
	public void addDocument(UserProfile userProfile, Path path, MultipartFile file, String type, String name) {
		Document document = new Document();
		
		String documentName = FilenameUtils.getBaseName(file.getOriginalFilename());
		if(name != null) {
			name = name.trim();
			if(!name.isEmpty()) {			
				documentName = name;
			}
		}
		
		document.setPath(path.toString());
		document.setName(documentName);
		document.setType(Document.TYPE.valueOf(type));
		document.setSize(file.getSize());
		document.setDateUploaded(LocalDate.now());
		
		userProfile.addDocument(document);
		
		userProfileRepository.save(userProfile);
	}

	
}
