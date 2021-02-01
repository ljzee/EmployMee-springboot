package com.employmee.employmee.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employmee.employmee.entity.User;
import com.employmee.employmee.payload.request.AuthenticateRequest;
import com.employmee.employmee.payload.request.RegisterRequest;
import com.employmee.employmee.payload.response.AuthenticateResponse;
import com.employmee.employmee.repository.BusinessProfileRepository;
import com.employmee.employmee.repository.UserProfileRepository;
import com.employmee.employmee.security.JwtUtils;
import com.employmee.employmee.security.MyUserDetails;
import com.employmee.employmee.service.RegistrationService;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	RegistrationService registrationService;
	
	@Autowired
	UserProfileRepository userProfileRepository;
	
	@Autowired
	BusinessProfileRepository businessProfileRepository;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticateUser(@RequestBody AuthenticateRequest authenticateRequest) {
			// BadCredentialsException exceptions are handled by the RestExceptionHandler class
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticateRequest.getUsername(), authenticateRequest.getPassword()));
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
			MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
			String jwt = jwtUtils.generateJwtToken(userDetails);
			
			boolean doesUserHaveProfile = this.doesUserHaveProfile(userDetails.getId());
			
			return ResponseEntity.ok(new AuthenticateResponse(userDetails.getId(), 
					                                          userDetails.getUsername(), 
					                                          userDetails.getEmail(), 
					                                          userDetails.getRole(),
					                                          jwt,
					                                          doesUserHaveProfile));
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
		User user = registrationService.processRegistration(registerRequest);
		
		MyUserDetails userDetails = new MyUserDetails(user);
		String jwt = jwtUtils.generateJwtToken(userDetails);
		
		boolean doesUserHaveProfile = this.doesUserHaveProfile(userDetails.getId());
		
		return ResponseEntity.ok(new AuthenticateResponse(userDetails.getId(), 
				                                          userDetails.getUsername(), 
				                                          userDetails.getEmail(), 
				                                          userDetails.getRole(),
				                                          jwt,
				                                          doesUserHaveProfile));
	}
	
	private boolean doesUserHaveProfile(int id) {
		if(userProfileRepository.existsById(id)) {
			return true;
		}
		
		if(businessProfileRepository.existsById(id)) {
			return true;
		}
		
		return false;
	}
}
