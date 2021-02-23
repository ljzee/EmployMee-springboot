package com.employmee.employmee.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employmee.employmee.entity.Address;
import com.employmee.employmee.entity.BusinessProfile;
import com.employmee.employmee.entity.JobPost;
import com.employmee.employmee.exception.EntityNotFoundException;
import com.employmee.employmee.exception.UserFriendlyException;
import com.employmee.employmee.payload.request.CreateBusinessProfileRequest;
import com.employmee.employmee.payload.request.CreateJobPostRequest;
import com.employmee.employmee.payload.request.UpdateJobPostDeadlineRequest;
import com.employmee.employmee.payload.request.UpdateJobPostStatusRequest;
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
	public ResponseEntity<?> getJobPosts() {
		BusinessProfile businessProfile = this.getCurrentBusinessProfile();
		
		List<BusinessJobPost> businessJobPosts = new ArrayList<>();
		for(JobPost jobPost : businessProfile.getJobPosts()) {
			businessJobPosts.add(new BusinessJobPost(jobPost));
		}
		
		return ResponseEntity.ok(businessJobPosts);
	}
	
	@GetMapping("/jobpost/{jobPostId}")
	@PreAuthorize("hasRole('BUSINESS')")
	public ResponseEntity<?> getJobPost(@PathVariable int jobPostId) {
		BusinessProfile businessProfile = this.getCurrentBusinessProfile();
		
		Optional<JobPost> jobPostOptional = jobPostRepository.findById(jobPostId);
		
		// if job post does not exist or does not belong to the business, send response with code 403
		if(jobPostOptional.isEmpty() || 
		   jobPostOptional.get().getBusinessProfile().getId() != businessProfile.getId()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		BusinessJobPost businessJobPost = new BusinessJobPost(jobPostOptional.get());
		return ResponseEntity.ok(businessJobPost);
	}
	
	@PostMapping("/jobpost")
	@PreAuthorize("hasRole('BUSINESS')")
	public ResponseEntity<?> createJobPost(@Valid @RequestBody CreateJobPostRequest createJobPostRequest) {
		BusinessProfile businessProfile = this.getCurrentBusinessProfile();
		
		jobPostService.createJobPost(businessProfile, createJobPostRequest);
		
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/jobpost/{jobPostId}")
	@PreAuthorize("hasRole('BUSINESS')")
	public ResponseEntity<?> updateJobPost(@PathVariable int jobPostId, @Valid @RequestBody CreateJobPostRequest createJobPostRequest) {
		BusinessProfile businessProfile = this.getCurrentBusinessProfile();
		
		Optional<JobPost> jobPostOptional = jobPostRepository.findById(jobPostId);
		
		// if job post does not exist or does not belong to the business, send response with code 403
		if(jobPostOptional.isEmpty() || 
		   jobPostOptional.get().getBusinessProfile().getId() != businessProfile.getId()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		jobPostService.updateJobPost(jobPostOptional.get(), createJobPostRequest);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/jobpost/{jobPostId}")
	@PreAuthorize("hasRole('BUSINESS')")
	public ResponseEntity<?> deleteJobPost(@PathVariable int jobPostId) {
		BusinessProfile businessProfile = this.getCurrentBusinessProfile();
		
		Optional<JobPost> jobPostOptional = jobPostRepository.findById(jobPostId);
		
		// if job post does not exist or does not belong to business, send response with code 403
		if(jobPostOptional.isEmpty() || 
		   jobPostOptional.get().getBusinessProfile().getId() != businessProfile.getId()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		// if deletion is not allowed for job post, send response with code 405
		if(!jobPostOptional.get().canDeleteJobPost()) {
			return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
		}
		
		jobPostRepository.delete(jobPostOptional.get());
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/jobpost/{jobPostId}/deadline")
	@PreAuthorize("hasRole('BUSINESS')")
	public ResponseEntity<?> updateJobPostDeadline(@PathVariable int jobPostId, @Valid @RequestBody UpdateJobPostDeadlineRequest updateJobPostDeadlineRequest) {
		BusinessProfile businessProfile = this.getCurrentBusinessProfile();
		
		Optional<JobPost> jobPostOptional = jobPostRepository.findById(jobPostId);
		
		// if job post does not exist or does not belong to the business, send response with code 403
		if(jobPostOptional.isEmpty() || 
		   jobPostOptional.get().getBusinessProfile().getId() != businessProfile.getId()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		jobPostService.updateJobPostDeadline(jobPostOptional.get(), updateJobPostDeadlineRequest);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/jobpost/{jobPostId}/status")
	@PreAuthorize("hasRole('BUSINESS')")
	public ResponseEntity<?> updateJobPostStatus(@PathVariable int jobPostId, @Valid @RequestBody UpdateJobPostStatusRequest updateJobPostStatusRequest) {
		BusinessProfile businessProfile = this.getCurrentBusinessProfile();
		
		Optional<JobPost> jobPostOptional = jobPostRepository.findById(jobPostId);
		
		// if job post does not exist or does not belong to the business, send response with code 403
		if(jobPostOptional.isEmpty() || 
		   jobPostOptional.get().getBusinessProfile().getId() != businessProfile.getId()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		jobPostService.updateJobPostStatus(jobPostOptional.get(), updateJobPostStatusRequest);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/address")
	@PreAuthorize("hasRole('BUSINESS')")
	public ResponseEntity<?> getBusinessAddresses() {
		BusinessProfile businessProfile = this.getCurrentBusinessProfile();
		
		ArrayList<Address> addresses = new ArrayList<>();
		addresses.addAll(businessProfile.getAddresses());
		
		return ResponseEntity.ok(addresses);
	}
	
	private BusinessProfile getCurrentBusinessProfile() {
		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Optional<BusinessProfile> result = businessProfileRepository.findById(userDetails.getId());
		return result.get();
	}
}
