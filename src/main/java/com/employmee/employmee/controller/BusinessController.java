package com.employmee.employmee.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.employmee.employmee.entity.Update;
import com.employmee.employmee.payload.request.AddUpdateRequest;
import com.employmee.employmee.payload.request.CreateBusinessProfileRequest;
import com.employmee.employmee.payload.request.CreateJobPostRequest;
import com.employmee.employmee.payload.request.UpdateBusinessProfileRequest;
import com.employmee.employmee.payload.request.UpdateJobPostDeadlineRequest;
import com.employmee.employmee.payload.request.UpdateJobPostStatusRequest;
import com.employmee.employmee.payload.request.UpdateProfileImageRequest;
import com.employmee.employmee.payload.response.Applicant;
import com.employmee.employmee.payload.response.BusinessDashboardResponse;
import com.employmee.employmee.payload.response.BusinessJobPost;
import com.employmee.employmee.payload.response.BusinessProfileResponse;
import com.employmee.employmee.repository.ApplicationRepository;
import com.employmee.employmee.repository.BusinessProfileRepository;
import com.employmee.employmee.repository.JobPostRepository;
import com.employmee.employmee.repository.UpdateRepository;
import com.employmee.employmee.security.MyUserDetails;
import com.employmee.employmee.service.ApplicationService;
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
	ApplicationService applicationService;
	
	@Autowired
	BusinessProfileRepository businessProfileRepository;
	
	@Autowired
	JobPostRepository jobPostRepository;
	
	@Autowired
	ApplicationRepository applicationRepository;
	
	@Autowired
	UpdateRepository updateRepository;
	
	@PostMapping("/profile")
	@PreAuthorize("hasRole('BUSINESS')")
	public ResponseEntity<?> createProfile(@Valid @RequestBody CreateBusinessProfileRequest createBusinessProfileRequest) {
		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		businessService.createProfile(userDetails.getId(), createBusinessProfileRequest);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/profile/{businessProfileId}")
	@PreAuthorize("hasAnyRole('USER', 'BUSINESS')")
	public ResponseEntity<?> getProfile(@PathVariable int businessProfileId) {
		Optional<BusinessProfile> businessProfileOptional = businessProfileRepository.findById(businessProfileId);
		
		if(businessProfileOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		BusinessProfile businessProfile = businessProfileOptional.get();
		List<Update> updates = updateRepository.getUpdatesByBusinessProfileId(businessProfileId);

		BusinessProfileResponse businessProfileResponse = new BusinessProfileResponse(businessProfile, updates);
		
		return ResponseEntity.ok(businessProfileResponse);
	}
	
	@PutMapping("/profile")
	@PreAuthorize("hasRole('BUSINESS')")
	public ResponseEntity<?> updateProfile(@Valid @RequestBody UpdateBusinessProfileRequest updateBusinessProfileRequest) {
		BusinessProfile businessProfile = this.getCurrentBusinessProfile();
		
		businessService.updateProfile(businessProfile, updateBusinessProfileRequest);
	
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/profile-image")
	@PreAuthorize("hasRole('BUSINESS')")
	public ResponseEntity<?> updateProfileImage(@Valid @RequestBody UpdateProfileImageRequest updateProfileImageRequest) {
		BusinessProfile businessProfile = this.getCurrentBusinessProfile();
		
		businessService.updateProfileImage(businessProfile, updateProfileImageRequest);
		
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
	
	@GetMapping("/jobpost/{jobPostId}/applicants")
	@PreAuthorize("hasRole('BUSINESS')")
	public ResponseEntity<?> getJobPostApplicants(@PathVariable int jobPostId) {
		Optional<JobPost> jobPostOptional = jobPostRepository.findById(jobPostId);
		if(jobPostOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		JobPost jobPost = jobPostOptional.get();
		BusinessProfile businessProfile = this.getCurrentBusinessProfile();
		if(!businessProfile.hasJobPost(jobPost)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		List<Applicant> applicants = jobPostService.getApplicantsForJobPost(jobPost);
		return ResponseEntity.ok(applicants);
	}
	
	@GetMapping("/address")
	@PreAuthorize("hasRole('BUSINESS')")
	public ResponseEntity<?> getBusinessAddresses() {
		BusinessProfile businessProfile = this.getCurrentBusinessProfile();
		
		ArrayList<Address> addresses = new ArrayList<>();
		addresses.addAll(businessProfile.getAddresses());
		
		return ResponseEntity.ok(addresses);
	}
	
	@PostMapping("/update")
	@PreAuthorize("hasRole('BUSINESS')")
	public ResponseEntity<?> addUpdate(@Valid @RequestBody AddUpdateRequest addUpdateRequest) {
		BusinessProfile businessProfile = this.getCurrentBusinessProfile();
		
		businessService.addUpdate(businessProfile, addUpdateRequest);
		
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/update/{updateId}")
	@PreAuthorize("hasRole('BUSINESS')")
	public ResponseEntity<?> deleteUpdate(@PathVariable int updateId) {
		Optional<Update> updateOptional = updateRepository.findById(updateId);
		BusinessProfile businessProfile = this.getCurrentBusinessProfile();
		if(updateOptional.isEmpty() ||
		   (updateOptional.get().getBusinessProfile().getId() != businessProfile.getId())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		updateRepository.delete(updateOptional.get());
		
		return ResponseEntity.ok().build();
	}
	
	private BusinessProfile getCurrentBusinessProfile() {
		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Optional<BusinessProfile> result = businessProfileRepository.findById(userDetails.getId());
		return result.get();
	}
}
