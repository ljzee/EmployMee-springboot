package com.employmee.employmee.service;

import com.employmee.employmee.entity.Experience;
import com.employmee.employmee.payload.request.AddExperienceRequest;

public interface ExperienceService {
	public void updateExperience(Experience experience, AddExperienceRequest addExperienceRequest);
}
