package com.employmee.employmee.service;

import java.util.List;

import com.employmee.employmee.entity.Application;
import com.employmee.employmee.entity.Document;
import com.employmee.employmee.entity.JobPost;
import com.employmee.employmee.entity.UserProfile;
import com.employmee.employmee.payload.request.UpdateApplicationStatusRequest;

public interface ApplicationService {
	public void createApplication(UserProfile userProfile, JobPost jobPost, List<Document> documents);
	
	public void updateApplicationStatus(Application application, UpdateApplicationStatusRequest updateApplicationStatusRequest);
}
