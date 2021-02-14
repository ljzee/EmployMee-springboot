package com.employmee.employmee.controller;

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

import com.employmee.employmee.entity.BusinessProfile;
import com.employmee.employmee.entity.UserProfile;
import com.employmee.employmee.payload.request.CreateBusinessProfileRequest;
import com.employmee.employmee.payload.response.BusinessDashboardResponse;
import com.employmee.employmee.payload.response.UserDashboardResponse;
import com.employmee.employmee.repository.BusinessProfileRepository;
import com.employmee.employmee.security.MyUserDetails;
import com.employmee.employmee.service.BusinessService;

@RestController
@RequestMapping("/business")
public class BusinessController {

	@Autowired
	BusinessService businessService;
	
	@Autowired
	BusinessProfileRepository businessProfileRepository;
	
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
		
		Optional<BusinessProfile> result = businessProfileRepository.findById(userDetails.getId());
		BusinessProfile businessProfile = result.get();
		
		BusinessDashboardResponse businessDashboardResponse = new BusinessDashboardResponse();
		businessDashboardResponse.setJobs(businessProfile.getJobPosts());
		
		return ResponseEntity.ok(businessDashboardResponse);
	}
	
}
