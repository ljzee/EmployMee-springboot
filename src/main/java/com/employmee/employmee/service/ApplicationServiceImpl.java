package com.employmee.employmee.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employmee.employmee.entity.Application;
import com.employmee.employmee.entity.Document;
import com.employmee.employmee.entity.JobPost;
import com.employmee.employmee.entity.UserProfile;
import com.employmee.employmee.repository.ApplicationRepository;

@Service
public class ApplicationServiceImpl implements ApplicationService {

	@Autowired
	ApplicationRepository applicationRepository;
	
	@Override
	public void createApplication(UserProfile userProfile, JobPost jobPost, List<Document> documents) {
		Application application = new Application();
		application.setStatus(Application.STATUS.NEW);
		application.setDateSubmitted(LocalDate.now());
		application.setJobPost(jobPost);
		application.setDocuments(new HashSet<Document>(documents));
		application.setUserProfile(userProfile);
		application.setJobPost(jobPost);
		
		applicationRepository.save(application);
	}

}
