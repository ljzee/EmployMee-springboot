package com.employmee.employmee.controller;

import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employmee.employmee.entity.UserProfile;
import com.employmee.employmee.payload.request.CreateUserProfileRequest;
import com.employmee.employmee.payload.response.UserDashboardResponse;
import com.employmee.employmee.repository.UserProfileRepository;
import com.employmee.employmee.security.MyUserDetails;
import com.employmee.employmee.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;

	@Autowired
	UserProfileRepository userProfileRepository;
	
	@PostMapping("/profile")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> createProfile(@Valid @RequestBody CreateUserProfileRequest createUserProfileRequest) {
		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		userService.createProfile(userDetails.getId(), createUserProfileRequest);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/dashboard")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getUserDashboard() {
		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Optional<UserProfile> result = userProfileRepository.findById(userDetails.getId());
		UserProfile userProfile = result.get();
		
		UserDashboardResponse userDashboardResponse = new UserDashboardResponse();
		userDashboardResponse.setBookmarks(userProfile.getBookmarkedJobPosts());
		
		return ResponseEntity.ok(userDashboardResponse);
	}
}
