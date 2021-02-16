package com.employmee.employmee.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

import com.employmee.employmee.entity.BusinessProfile;
import com.employmee.employmee.entity.JobPost;
import com.employmee.employmee.payload.request.CreateBusinessProfileRequest;
import com.employmee.employmee.payload.request.CreateJobPostRequest;
import com.employmee.employmee.payload.response.BusinessDashboardResponse;
import com.employmee.employmee.payload.response.BusinessJobPost;
import com.employmee.employmee.repository.BusinessProfileRepository;
import com.employmee.employmee.repository.JobPostRepository;
import com.employmee.employmee.security.MyUserDetails;
import com.employmee.employmee.service.BusinessService;
import com.employmee.employmee.service.JobPostService;

@RestController
@RequestMapping("/business")
public class BusinessController {

	@Autowired
	BusinessService businessService;
	
	@Autowired
	JobPostService jobPostService;
	
	@Autowired
	BusinessProfileRepository businessProfileRepository;
	
	@Autowired
	JobPostRepository jobPostRepository;

	
	@PostMapping("/profile")
	@PreAuthorize("hasRole('BUSINESS')")
	public ResponseEntity<?> createProfile(@Valid @RequestBody CreateBusinessProfileRequest createBusinessProfileRequest) {
		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		businessService.createProfile(userDetails.getId(), createBusinessProfileRequest);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/dashboard")
	@PreAuthorize("hasRole('BUSINESS')")
	public ResponseEntity<?> getUserDashboard() {
		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		BusinessDashboardResponse businessDashboardResponse = new BusinessDashboardResponse();
		List<JobPost> openBusinessJobPosts = jobPostRepository.findByBusinessProfileIdAndStatus(userDetails.getId(), JobPost.STATUS.OPEN);
		businessDashboardResponse.setJobs(openBusinessJobPosts);
		
		return ResponseEntity.ok(businessDashboardResponse);
	}
	
	@GetMapping("/jobpost")
	@PreAuthorize("hasRole('BUSINESS')")
	public ResponseEntity<?> getBusinessJobPosts() {
		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Optional<BusinessProfile> result = businessProfileRepository.findById(userDetails.getId());
		BusinessProfile businessProfile = result.get();
		
		List<BusinessJobPost> businessJobPosts = new ArrayList<>();
		for(JobPost jobPost : businessProfile.getJobPosts()) {
			businessJobPosts.add(new BusinessJobPost(jobPost));
		}
		
		return ResponseEntity.ok(businessJobPosts);
	}
	
	@PostMapping("/jobpost")
	@PreAuthorize("hasRole('BUSINESS')")
	public ResponseEntity<?> createJobPost(@Valid @RequestBody CreateJobPostRequest createJobPostRequest) {
		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Optional<BusinessProfile> result = businessProfileRepository.findById(userDetails.getId());
		BusinessProfile businessProfile = result.get();
		
		jobPostService.createJobPost(businessProfile, createJobPostRequest);
		
		return ResponseEntity.ok().build();
	}
}
