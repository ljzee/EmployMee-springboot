package com.employmee.employmee.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employmee.employmee.payload.request.CreateBusinessProfileRequest;
import com.employmee.employmee.security.MyUserDetails;
import com.employmee.employmee.service.BusinessService;

@RestController
@RequestMapping("/business")
public class BusinessController {

	@Autowired
	BusinessService businessService;
	
	@PostMapping("/profile")
	@PreAuthorize("hasRole('BUSINESS')")
	public ResponseEntity<?> createProfile(@Valid @RequestBody CreateBusinessProfileRequest createBusinessProfileRequest) {
		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		businessService.createProfile(userDetails.getId(), createBusinessProfileRequest);
		return ResponseEntity.ok().build();
	}
	
}
