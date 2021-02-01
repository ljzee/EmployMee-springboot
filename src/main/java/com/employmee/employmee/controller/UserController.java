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

import com.employmee.employmee.payload.request.CreateUserProfileRequest;
import com.employmee.employmee.security.MyUserDetails;
import com.employmee.employmee.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/profile")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> createProfile(@Valid @RequestBody CreateUserProfileRequest createUserProfileRequest) {
		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		userService.createProfile(userDetails.getId(), createUserProfileRequest);
		return ResponseEntity.ok().build();
	}
}
