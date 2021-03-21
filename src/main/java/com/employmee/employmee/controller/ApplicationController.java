package com.employmee.employmee.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.employmee.employmee.entity.Application;
import com.employmee.employmee.entity.BusinessProfile;
import com.employmee.employmee.entity.Document;
import com.employmee.employmee.entity.JobPost;
import com.employmee.employmee.entity.UserProfile;
import com.employmee.employmee.exception.UserFriendlyException;
import com.employmee.employmee.payload.request.SubmitApplicationRequest;
import com.employmee.employmee.payload.request.UpdateApplicationStatusRequest;
import com.employmee.employmee.repository.ApplicationRepository;
import com.employmee.employmee.repository.BusinessProfileRepository;
import com.employmee.employmee.repository.DocumentRepository;
import com.employmee.employmee.repository.JobPostRepository;
import com.employmee.employmee.repository.UserProfileRepository;
import com.employmee.employmee.security.MyUserDetails;
import com.employmee.employmee.service.ApplicationService;
import com.employmee.employmee.service.StorageService;

@RestController
@RequestMapping("/application")
public class ApplicationController {
	
	@Autowired
	ApplicationService applicationService;
	
	@Autowired
	StorageService storageService;
	
	@Autowired
	UserProfileRepository userProfileRepository;
	
	@Autowired
	BusinessProfileRepository businessProfileRepository;
	
	@Autowired
	JobPostRepository jobPostRepository;
	
	@Autowired
	DocumentRepository documentRepository;
	
	@Autowired
	ApplicationRepository applicationRepository;
	
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
	
	@PutMapping("/{applicationId}")
	@PreAuthorize("hasRole('BUSINESS')")
	public ResponseEntity<?> updateApplicationStatus(@PathVariable int applicationId, @Valid @RequestBody UpdateApplicationStatusRequest updateApplicationStatusRequest) {
		Optional<Application> applicationOptional = applicationRepository.findById(applicationId);
		if(applicationOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
		Application application = applicationOptional.get();
		BusinessProfile businessProfile = this.getCurrentBusinessProfile();
		if(!businessProfile.hasJobPost(application.getJobPost())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		applicationService.updateApplicationStatus(application, updateApplicationStatusRequest);
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{applicationId}")
	@PreAuthorize("hasRole('BUSINESS')")
	public ResponseEntity<StreamingResponseBody> serveApplicationDocumentsAsZip(@PathVariable int applicationId) {
		Optional<Application> applicationOptional = applicationRepository.findById(applicationId);
		if(applicationOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
		Application application = applicationOptional.get();
		BusinessProfile businessProfile = this.getCurrentBusinessProfile();
		if(!businessProfile.hasJobPost(application.getJobPost())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		Set<Document> documents = application.getDocuments();
		String zipName = this.getZipName(application);
		return ResponseEntity.ok()
				             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + zipName + "\"")
				             .body(out -> {
				                 var zipOutputStream = new ZipOutputStream(out);
				                 for(Document document : documents) {
				                	 Resource resource = storageService.loadAsResource(FilenameUtils.getName(document.getPath()));
				                	 File file = resource.getFile();
				                	 
				                	 zipOutputStream.putNextEntry(new ZipEntry(document.getFileName()));
				                	 FileInputStream fileInputStream = new FileInputStream(file);
				                	 
				                     IOUtils.copy(fileInputStream, zipOutputStream);

				                     fileInputStream.close();
				                     zipOutputStream.closeEntry();
				                 }
				                 zipOutputStream.close();
				             });
	}
	
	// Example zip name: software-developer-newuser-package.zip
	private String getZipName(Application application) {
		String template = "%s-%s%s-package.zip";
		
		String jobTitleProcessed = application.getJobPost().getTitle().toLowerCase().replaceAll("[^A-Za-z0-9]+", "-");
		String firstNameProcessed = application.getUserProfile().getFirstName().toLowerCase().replaceAll("[^A-Za-z0-9]+", "-");
		String lastNameProcessed = application.getUserProfile().getLastName().toLowerCase().replaceAll("[^A-Za-z0-9]+", "-");
		
		return String.format(template, jobTitleProcessed, firstNameProcessed, lastNameProcessed);
	}
	
	private UserProfile getCurrentUserProfile() {
		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Optional<UserProfile> result = userProfileRepository.findById(userDetails.getId());
		return result.get();
	}
	
	private BusinessProfile getCurrentBusinessProfile() {
		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Optional<BusinessProfile> result = businessProfileRepository.findById(userDetails.getId());
		return result.get();
	}
}
