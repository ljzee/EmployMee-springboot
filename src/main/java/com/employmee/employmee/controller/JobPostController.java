package com.employmee.employmee.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employmee.employmee.entity.UserProfile;
import com.employmee.employmee.payload.response.UserJobPost;
import com.employmee.employmee.repository.UserProfileRepository;
import com.employmee.employmee.security.MyUserDetails;
import com.employmee.employmee.service.JobPostService;

@RestController
@RequestMapping("/jobpost")
public class JobPostController {
	@Autowired
	JobPostService jobPostService;
	
	@Autowired
	UserProfileRepository userProfileRepository;
	
	@GetMapping("")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> searchJobPost(@RequestParam(defaultValue = "") String searchField, 
			                               @RequestParam(defaultValue = "") String country,
			                               @RequestParam(defaultValue = "") String state,
			                               @RequestParam(defaultValue = "") String city) {
		
		UserProfile userProfile = this.getCurrentUserProfile();
		List<UserJobPost> userJobPosts = jobPostService.searchJobPosts(userProfile, searchField, country, state, city);
		
		return ResponseEntity.ok(userJobPosts);
	}
	
	@GetMapping("/{jobPostId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getJobPost(@PathVariable int jobPostId) {
		UserProfile userProfile = this.getCurrentUserProfile();
		
		UserJobPost userJobPost = jobPostService.getJobPostById(userProfile, jobPostId);
		if(userJobPost == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(userJobPost);
	}
	
	private UserProfile getCurrentUserProfile() {
		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
		
		Optional<UserProfile> result = userProfileRepository.findById(userDetails.getId());
		UserProfile userProfile = result.get();
		return userProfile;
	}
}
