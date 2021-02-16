package com.employmee.employmee.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employmee.employmee.entity.Address;
import com.employmee.employmee.entity.BusinessProfile;
import com.employmee.employmee.entity.JobPost;
import com.employmee.employmee.exception.UserFriendlyException;
import com.employmee.employmee.payload.request.CreateJobPostRequest;
import com.employmee.employmee.repository.AddressRepository;
import com.employmee.employmee.repository.BusinessProfileRepository;

@Service
public class JobPostServiceImpl implements JobPostService {
	@Autowired
	AddressRepository addressRepository;
	
	@Autowired
	BusinessProfileRepository businessProfileRepository;
	
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
		
		businessProfile.addJobPost(newJobPost);
		
		businessProfileRepository.save(businessProfile);
		
	}

}
