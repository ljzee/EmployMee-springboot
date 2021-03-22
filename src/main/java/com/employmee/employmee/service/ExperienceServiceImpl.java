package com.employmee.employmee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employmee.employmee.entity.Experience;
import com.employmee.employmee.payload.request.AddExperienceRequest;
import com.employmee.employmee.repository.ExperienceRepository;

@Service
public class ExperienceServiceImpl implements ExperienceService {

	@Autowired
	ExperienceRepository experienceRepository;
	
	@Override
	public void updateExperience(Experience experience, AddExperienceRequest addExperienceRequest) {
		experience.setCompanyName(addExperienceRequest.getCompanyName());
		experience.setTitle(addExperienceRequest.getTitle());
		experience.setLocation(addExperienceRequest.getLocation());
		experience.setDescription(addExperienceRequest.getDescription());
		experience.setStartDate(addExperienceRequest.getStartDate());
		experience.setEndDate(addExperienceRequest.getEndDate());
		
		experienceRepository.save(experience);
	}

}
