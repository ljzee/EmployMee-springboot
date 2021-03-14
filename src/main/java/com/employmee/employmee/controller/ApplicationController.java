package com.employmee.employmee.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employmee.employmee.entity.Application;
import com.employmee.employmee.entity.Document;
import com.employmee.employmee.entity.JobPost;
import com.employmee.employmee.entity.UserProfile;
import com.employmee.employmee.exception.UserFriendlyException;
import com.employmee.employmee.payload.request.SubmitApplicationRequest;
import com.employmee.employmee.repository.DocumentRepository;
import com.employmee.employmee.repository.JobPostRepository;
import com.employmee.employmee.repository.UserProfileRepository;
import com.employmee.employmee.security.MyUserDetails;
import com.employmee.employmee.service.ApplicationService;

@RestController
@RequestMapping("/application")
public class ApplicationController {
	
	@Autowired
	ApplicationService applicationService;
	
	@Autowired
	UserProfileRepository userProfileRepository;
	
	@Autowired
	JobPostRepository jobPostRepository;
	
	@Autowired
	DocumentRepository documentRepository;
	
	@PostMapping("")
	public ResponseEntity<?> submitApplication(@Valid @RequestBody SubmitApplicationRequest submitApplicationRequest) {
		// Find the job post for the application
		Optional<JobPost> jobPostOptional = jobPostRepository.findById(submitApplicationRequest.getJobPostId());
		JobPost jobPost = jobPostOptional.orElseThrow(() -> new UserFriendlyException("Job post does not exist."));
		
		// Verify that job post is currently accepting applications
		this.checkJobPostAcceptingApplications(jobPost);
		
		// Verify that the user has not already submitted an application for the job post
		UserProfile userProfile = this.getCurrentUserProfile();
		this.checkUserAppliedToJobPost(userProfile, jobPost);
		
		// Verify that the selected documents exist and belong to the user
		List<Document> documents = this.getDocuments(submitApplicationRequest.getDocumentIds());
		this.checkUserOwnDocuments(userProfile, documents);
		
		// Verify user has selected all required documents for the job post
		this.checkRequiredDocuments(jobPost, documents);
		
		applicationService.createApplication(userProfile, jobPost, documents);
		
		return ResponseEntity.ok().build();
	}
	
	private void checkJobPostAcceptingApplications(JobPost jobPost) {
		if(!jobPost.isAcceptingApplications()) {
			throw new UserFriendlyException("Job post is currently not accepting applications.");
		}
	}
	
	private void checkUserAppliedToJobPost(UserProfile userProfile, JobPost jobPost) {
		if(userProfile.hasAppliedToJobPost(jobPost)) {
			throw new UserFriendlyException("An application has already been submitted to this job post.");
		}
	}
	
	private List<Document> getDocuments(List<Integer> documentIds) {
		List<Document> documents = new ArrayList<>();
		for(Integer documentId : documentIds) {
			Optional<Document> documentOptional = documentRepository.findById(documentId);
			Document document = documentOptional.orElseThrow(() -> new UserFriendlyException("User does not own selected document."));
			documents.add(document);
		}
		return documents;
	}
	
	private void checkUserOwnDocuments(UserProfile userProfile, List<Document> documents) {
		for(Document document: documents) {
			if(!userProfile.hasDocument(document)) {
				throw new UserFriendlyException("User does not own selected document.");
			}
		}
	}
	
	private void checkRequiredDocuments(JobPost jobPost, List<Document> documents) {
		boolean hasSelectedResume = documents.stream().filter(document -> document.getType() == Document.TYPE.RESUME).findFirst().isPresent();
		if(jobPost.isResumeRequired() && !hasSelectedResume) {
			throw new UserFriendlyException("A resume is required for this application.");
		}
		
		boolean hasSelectedCoverletter = documents.stream().filter(document -> document.getType() == Document.TYPE.COVERLETTER).findFirst().isPresent();
		if(jobPost.isCoverletterRequired() && !hasSelectedCoverletter) {
			throw new UserFriendlyException("A cover letter is required for this application.");
		}
		
		boolean hasSelectedOther = documents.stream().filter(document -> document.getType() == Document.TYPE.OTHER).findFirst().isPresent();
		if(jobPost.isOtherRequired() && !hasSelectedOther) {
			throw new UserFriendlyException("A document of type OTHER is required for this application.");
		}
	}
	
	private UserProfile getCurrentUserProfile() {
		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Optional<UserProfile> result = userProfileRepository.findById(userDetails.getId());
		return result.get();
	}
}
