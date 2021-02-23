package com.employmee.employmee.service;

import java.time.LocalDate;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employmee.employmee.entity.Address;
import com.employmee.employmee.entity.BusinessProfile;
import com.employmee.employmee.entity.JobPost;
import com.employmee.employmee.exception.UserFriendlyException;
import com.employmee.employmee.payload.request.CreateJobPostRequest;
import com.employmee.employmee.payload.request.UpdateJobPostDeadlineRequest;
import com.employmee.employmee.payload.request.UpdateJobPostStatusRequest;
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

}
