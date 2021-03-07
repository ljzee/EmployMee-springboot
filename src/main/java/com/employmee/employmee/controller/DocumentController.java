package com.employmee.employmee.controller;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.employmee.employmee.annotation.ValueOfEnum;
import com.employmee.employmee.entity.Document;
import com.employmee.employmee.entity.UserProfile;
import com.employmee.employmee.repository.UserProfileRepository;
import com.employmee.employmee.security.MyUserDetails;
import com.employmee.employmee.service.StorageService;
import com.employmee.employmee.service.UserService;

@Validated
@RestController
@RequestMapping("/document")
public class DocumentController {
	
	@Autowired
	UserProfileRepository userProfileRepository;
	
	@Autowired
	StorageService storageService;
	
	@Autowired
	UserService userService;
	
	@GetMapping("")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getUserDocuments() {
		UserProfile userProfile = this.getCurrentUserProfile();
		
		return ResponseEntity.ok(new ArrayList<String>());
	}
	
	@PostMapping("")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> uploadDocument(@RequestParam MultipartFile file, 
											@RequestParam @ValueOfEnum(enumClass = Document.TYPE.class, message = "Document must be one of RESUME, COVERLETTER, or OTHER.") String type, 
											@RequestParam String name) {
		
		Path filePath = storageService.store(file);
		
		UserProfile userProfile = this.getCurrentUserProfile();
		userService.addDocument(userProfile, filePath, file, type, name);
		
		return ResponseEntity.ok().build();
	}
	
	private UserProfile getCurrentUserProfile() {
		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Optional<UserProfile> result = userProfileRepository.findById(userDetails.getId());
		return result.get();
	}
	
	
}
