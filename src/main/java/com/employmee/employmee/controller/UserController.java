package com.employmee.employmee.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employmee.employmee.entity.Application;
import com.employmee.employmee.entity.Experience;
import com.employmee.employmee.entity.JobPost;
import com.employmee.employmee.entity.UserProfile;
import com.employmee.employmee.payload.request.AddExperienceRequest;
import com.employmee.employmee.payload.request.BookmarkJobPostRequest;
import com.employmee.employmee.payload.request.CreateUserProfileRequest;
import com.employmee.employmee.payload.request.UpdateProfileImageRequest;
import com.employmee.employmee.payload.request.UpdateUserProfileRequest;
import com.employmee.employmee.payload.response.UserApplication;
import com.employmee.employmee.payload.response.UserDashboardResponse;
import com.employmee.employmee.payload.response.UserProfileResponse;
import com.employmee.employmee.repository.ExperienceRepository;
import com.employmee.employmee.repository.JobPostRepository;
import com.employmee.employmee.repository.UserProfileRepository;
import com.employmee.employmee.security.MyUserDetails;
import com.employmee.employmee.service.ExperienceService;
import com.employmee.employmee.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;

	@Autowired
	ExperienceService experienceService;
	
	@Autowired
	UserProfileRepository userProfileRepository;
	
	@Autowired
	JobPostRepository jobPostRepository;
	
	@Autowired
	ExperienceRepository experienceRepository;
	
	@PostMapping("/profile")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> createProfile(@Valid @RequestBody CreateUserProfileRequest createUserProfileRequest) {
		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		userService.createProfile(userDetails.getId(), createUserProfileRequest);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/profile/{userProfileId}")
	@PreAuthorize("hasAnyRole('USER', 'BUSINESS')")
	public ResponseEntity<?> getProfile(@PathVariable int userProfileId) {
		Optional<UserProfile> userProfileOptional = userProfileRepository.findById(userProfileId);
		if(userProfileOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		UserProfileResponse userProfileResponse = new UserProfileResponse(userProfileOptional.get());
		
		return ResponseEntity.ok().body(userProfileResponse);
	}
	
	@PostMapping("/profile-image")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> updateProfileImage(@Valid @RequestBody UpdateProfileImageRequest updateProfileImageRequest) {
		UserProfile userProfile = this.getCurrentUserProfile();
		
		userService.updateProfileImage(userProfile, updateProfileImageRequest);
		
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/profile")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> updateProfile(@Valid @RequestBody UpdateUserProfileRequest updateUserProfileRequest) {
		UserProfile userProfile = this.getCurrentUserProfile();
		
		userService.updateProfile(userProfile, updateUserProfileRequest);
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/dashboard")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getUserDashboard() {
		UserProfile userProfile = this.getCurrentUserProfile();
		
		UserDashboardResponse userDashboardResponse = new UserDashboardResponse();
		userDashboardResponse.setBookmarks(userProfile.getBookmarkedJobPosts());
		
		return ResponseEntity.ok(userDashboardResponse);
	}
	
	@PostMapping("/bookmark")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> toggleBookmarkJobPost(@Valid @RequestBody BookmarkJobPostRequest bookmarkJobPostRequest) {
		UserProfile userProfile = this.getCurrentUserProfile();
		
		// only job posts whose status is either OPEN or CLOSED can toggle bookmark
		Optional<JobPost> jobPostOptional = jobPostRepository.findFirstByIdAndStatusIn(bookmarkJobPostRequest.getJobPostId(), Arrays.asList(JobPost.STATUS.OPEN, JobPost.STATUS.CLOSED));
		if(jobPostOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
		userService.toggleBookmarkJobPost(userProfile, jobPostOptional.get());
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/experience")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> addExperience(@Valid @RequestBody AddExperienceRequest addExperienceRequest) {
		UserProfile userProfile = this.getCurrentUserProfile();
		
		userService.addExperience(userProfile, addExperienceRequest);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/experience/{experienceId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> updateExperience(@PathVariable int experienceId, @Valid @RequestBody AddExperienceRequest addExperienceRequest) {
		UserProfile userProfile = this.getCurrentUserProfile();
		
		Optional<Experience> experienceOptional = experienceRepository.findById(experienceId);
		if(experienceOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		Experience experience = experienceOptional.get();
		if(!userProfile.hasExperience(experience)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		experienceService.updateExperience(experience, addExperienceRequest);
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/application")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getAllApplications() {
		UserProfile userProfile = this.getCurrentUserProfile();
		Set<Application> applications = userProfile.getApplications();
		List<UserApplication> userApplications = applications.stream().map(application -> new UserApplication(application))
				                                                      .collect(Collectors.toList());
		return ResponseEntity.ok(userApplications);
				        						                      
	}
	
	private UserProfile getCurrentUserProfile() {
		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Optional<UserProfile> result = userProfileRepository.findById(userDetails.getId());
		return result.get();
	}
}
