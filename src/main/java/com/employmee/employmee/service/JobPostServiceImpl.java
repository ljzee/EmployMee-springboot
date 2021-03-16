package com.employmee.employmee.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employmee.employmee.entity.Address;
import com.employmee.employmee.entity.Application;
import com.employmee.employmee.entity.BusinessProfile;
import com.employmee.employmee.entity.JobPost;
import com.employmee.employmee.entity.UserProfile;
import com.employmee.employmee.exception.UserFriendlyException;
import com.employmee.employmee.payload.request.CreateJobPostRequest;
import com.employmee.employmee.payload.request.UpdateJobPostDeadlineRequest;
import com.employmee.employmee.payload.request.UpdateJobPostStatusRequest;
import com.employmee.employmee.payload.response.Applicant;
import com.employmee.employmee.payload.response.UserJobPost;
import com.employmee.employmee.repository.AddressRepository;
import com.employmee.employmee.repository.BusinessProfileRepository;
import com.employmee.employmee.repository.JobPostRepository;

@Service
public class JobPostServiceImpl implements JobPostService {
	@Autowired
	AddressRepository addressRepository;
	
	@Autowired
	BusinessProfileRepository businessProfileRepository;
	
	@Autowired
	JobPostRepository jobPostRepository;
	
	@Override
	public void createJobPost(BusinessProfile businessProfile, CreateJobPostRequest createJobPostRequest) {
		Optional<Address> addressOptional = addressRepository.findById(createJobPostRequest.getAddressId());
		Address address = addressOptional.orElseThrow(() -> new UserFriendlyException("Cannot create new job post. Address id is not valid."));
		
		JobPost newJobPost = new JobPost();
		newJobPost.setTitle(createJobPostRequest.getTitle());
		newJobPost.setDuration(createJobPostRequest.getDuration());
		newJobPost.setPositionType(JobPost.TYPE.valueOf(createJobPostRequest.getPositionType()));
		newJobPost.getAddresses().add(address);
		newJobPost.setOpenings(createJobPostRequest.getOpenings());
		newJobPost.setResumeRequired(createJobPostRequest.isResumeRequired());
		newJobPost.setCoverletterRequired(createJobPostRequest.isCoverletterRequired());
		newJobPost.setOtherRequired(createJobPostRequest.isOtherRequired());
		newJobPost.setStatus(JobPost.STATUS.valueOf(createJobPostRequest.getStatus()));
		newJobPost.setDescription(createJobPostRequest.getDescription());
		newJobPost.setSalary(createJobPostRequest.getSalary());
		newJobPost.setDeadline(createJobPostRequest.getDeadline());
		newJobPost.setDateCreated(LocalDate.now());
		
		if(createJobPostRequest.getStatus() == JobPost.STATUS.OPEN.name()) {
			newJobPost.setDatePublished(LocalDate.now());
		}
		
		businessProfile.addJobPost(newJobPost);
		
		businessProfileRepository.save(businessProfile);
		
	}

	@Override
	public void updateJobPostDeadline(JobPost jobPost, UpdateJobPostDeadlineRequest updateJobPostDeadlineRequest) {
		jobPost.setDeadline(updateJobPostDeadlineRequest.getDeadline());
		jobPostRepository.save(jobPost);
	}

	@Override
	public void updateJobPost(JobPost jobPost, CreateJobPostRequest createJobPostRequest) {
		Optional<Address> addressOptional = addressRepository.findById(createJobPostRequest.getAddressId());
		Address address = addressOptional.orElseThrow(() -> new UserFriendlyException("Cannot update job post. Address id is not valid."));
		
		jobPost.setTitle(createJobPostRequest.getTitle());
		jobPost.setDuration(createJobPostRequest.getDuration());
		jobPost.setPositionType(JobPost.TYPE.valueOf(createJobPostRequest.getPositionType()));
		jobPost.getAddresses().add(address);
		jobPost.setOpenings(createJobPostRequest.getOpenings());
		jobPost.setResumeRequired(createJobPostRequest.isResumeRequired());
		jobPost.setCoverletterRequired(createJobPostRequest.isCoverletterRequired());
		jobPost.setOtherRequired(createJobPostRequest.isOtherRequired());
		jobPost.setDescription(createJobPostRequest.getDescription());
		jobPost.setSalary(createJobPostRequest.getSalary());
		jobPost.setDeadline(createJobPostRequest.getDeadline());
		
		jobPostRepository.save(jobPost);
	}

	@Override
	public void updateJobPostStatus(JobPost jobPost, @Valid UpdateJobPostStatusRequest updateJobPostStatusRequest) {
		jobPost.setStatus(JobPost.STATUS.valueOf(updateJobPostStatusRequest.getStatus()));
		jobPostRepository.save(jobPost);
	}

	@Override
	public List<UserJobPost> searchJobPosts(UserProfile userProfile, String searchField, String country, String state, String city) {
		Set<JobPost> jobPosts = jobPostRepository.searchJobPost(searchField, country, state, city);
		
		// process searched job posts to determine if user has applied to or bookmarked any job searched job posts
		List<UserJobPost> userJobPosts = new ArrayList<>();
		Iterator<JobPost> jobPostIterator = jobPosts.iterator();
		while(jobPostIterator.hasNext()) {
			JobPost jobPost = jobPostIterator.next();
			UserJobPost userJobPost = new UserJobPost(jobPost);
			
			userJobPost.setApplied(userProfile.hasAppliedToJobPost(jobPost));
			userJobPost.setBookmarked(userProfile.hasBookmarkedJobPost(jobPost));
			
			userJobPosts.add(userJobPost);
		}
		
		return userJobPosts;
	}

	@Override
	public UserJobPost getJobPostById(UserProfile userProfile, int jobPostId) {
		Optional<JobPost> jobPostOptional = this.jobPostRepository.findFirstByIdAndStatusIn(jobPostId, Arrays.asList(JobPost.STATUS.OPEN, JobPost.STATUS.CLOSED));
		if(jobPostOptional.isEmpty()) {
			return null;
		}
		
		JobPost jobPost = jobPostOptional.get();
		UserJobPost userJobPost = new UserJobPost(jobPost);
		
		userJobPost.setApplied(userProfile.hasAppliedToJobPost(jobPost));
		userJobPost.setBookmarked(userProfile.hasBookmarkedJobPost(jobPost));
		
		return userJobPost;
	}

	@Override
	public List<Applicant> getApplicantsForJobPost(JobPost jobPost) {
		
		List<Applicant> applicants = jobPost.getApplications().stream()
				                                              .map(application -> new Applicant(application))
				                                              .collect(Collectors.toList());

		return applicants;
	}

}
