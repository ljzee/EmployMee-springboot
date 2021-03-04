package com.employmee.employmee.controller;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employmee.employmee.entity.UserProfile;
import com.employmee.employmee.repository.UserProfileRepository;
import com.employmee.employmee.security.MyUserDetails;

@RestController
@RequestMapping("/document")
public class DocumentController {
	
	@Autowired
	UserProfileRepository userProfileRepository;
	
	@GetMapping("")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getUserDocuments() {
		UserProfile userProfile = this.getCurrentUserProfile();
		
		return ResponseEntity.ok(new ArrayList<String>());
	}
	
	private UserProfile getCurrentUserProfile() {
		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Optional<UserProfile> result = userProfileRepository.findById(userDetails.getId());
		return result.get();
	}
}
