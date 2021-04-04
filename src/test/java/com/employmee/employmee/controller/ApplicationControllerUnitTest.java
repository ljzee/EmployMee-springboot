package com.employmee.employmee.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.employmee.employmee.entity.Application;
import com.employmee.employmee.entity.Document;
import com.employmee.employmee.entity.JobPost;
import com.employmee.employmee.entity.UserProfile;
import com.employmee.employmee.exception.UserFriendlyException;
import com.employmee.employmee.payload.request.SubmitApplicationRequest;
import com.employmee.employmee.repository.DocumentRepository;
import com.employmee.employmee.repository.JobPostRepository;
import com.employmee.employmee.repository.UserProfileRepository;
import com.employmee.employmee.security.MyUserDetails;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ApplicationControllerUnitTest {

	@Mock
	JobPostRepository jobPostRepository;
	
	@Mock
	UserProfileRepository userProfileRepository;
	
	@Mock
	DocumentRepository documentRepository;
	
	@InjectMocks
	ApplicationController applicationController;
	
	@Test
	void Given_SubmitApplicationRequestWithNonExistentJobPostId_When_SubmitApplication_Then_UserFriendlyExceptionThrown() {
		when(jobPostRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		
		SubmitApplicationRequest submitApplicationRequest = new SubmitApplicationRequest();
		submitApplicationRequest.setJobPostId(1);
		
		UserFriendlyException userFriendlyException = assertThrows(UserFriendlyException.class, () -> applicationController.submitApplication(submitApplicationRequest));
		assertEquals("Job post does not exist.", userFriendlyException.getUserFriendlyMessage());
	}
	
	@Test
	void Given_JobPostNotAcceptingApplications_When_SubmitApplication_Then_UserFriendlyExceptionThrown() {
		JobPost jobPost = new JobPost();
		jobPost.setStatus(JobPost.STATUS.CLOSED);
		
		when(jobPostRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(jobPost));
		
		SubmitApplicationRequest submitApplicationRequest = new SubmitApplicationRequest();
		submitApplicationRequest.setJobPostId(1);
		
		UserFriendlyException userFriendlyException = assertThrows(UserFriendlyException.class, () -> applicationController.submitApplication(submitApplicationRequest));
		assertEquals("Job post is currently not accepting applications.", userFriendlyException.getUserFriendlyMessage());
	}
	
	@Test
	void Given_SubmitApplicationRequestWithAlreadyAppliedJobPostId_When_SubmitApplication_Then_UserFriendlyExceptionThrown() {
		JobPost jobPost = new JobPost();
		jobPost.setStatus(JobPost.STATUS.OPEN);
		Application application = new Application();
		application.setJobPost(jobPost);
		UserProfile userProfile = new UserProfile();
		userProfile.addApplication(application);
		
		when(userProfileRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(userProfile));
		when(jobPostRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(jobPost));
		
		SubmitApplicationRequest submitApplicationRequest = new SubmitApplicationRequest();
		submitApplicationRequest.setJobPostId(1);
		
		UserFriendlyException userFriendlyException = assertThrows(UserFriendlyException.class, () -> applicationController.submitApplication(submitApplicationRequest));
		assertEquals("An application has already been submitted to this job post.", userFriendlyException.getUserFriendlyMessage());
	}

	@Test
	void Given_SubmitApplicationRequestWithNonExistentDocumentId_When_SubmitApplication_Then_UserFriendlyExceptionThrown() {
		UserProfile userProfile = new UserProfile();
		when(userProfileRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(userProfile));
		
		JobPost jobPost = new JobPost();
		jobPost.setStatus(JobPost.STATUS.OPEN);
		when(jobPostRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(jobPost));
		
		when(documentRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		
		SubmitApplicationRequest submitApplicationRequest = new SubmitApplicationRequest();
		submitApplicationRequest.setJobPostId(1);
		ArrayList<Integer> documentIds = new ArrayList<>();
		documentIds.add(1);
		submitApplicationRequest.setDocumentIds(documentIds);
		
		UserFriendlyException userFriendlyException = assertThrows(UserFriendlyException.class, () -> applicationController.submitApplication(submitApplicationRequest));
		assertEquals("User does not own selected document.", userFriendlyException.getUserFriendlyMessage());
	}
	
	@Test
	void Given_SubmitApplicationRequestWithDocumentIdNotOwnedByUser_When_SubmitApplication_Then_UserFriendlyExceptionThrown() {
		UserProfile userProfile = new UserProfile();
		when(userProfileRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(userProfile));
		
		JobPost jobPost = new JobPost();
		jobPost.setStatus(JobPost.STATUS.OPEN);
		when(jobPostRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(jobPost));
		
		Document document = new Document();
		when(documentRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(document));
		
		SubmitApplicationRequest submitApplicationRequest = new SubmitApplicationRequest();
		submitApplicationRequest.setJobPostId(1);
		ArrayList<Integer> documentIds = new ArrayList<>();
		documentIds.add(1);
		submitApplicationRequest.setDocumentIds(documentIds);
		
		UserFriendlyException userFriendlyException = assertThrows(UserFriendlyException.class, () -> applicationController.submitApplication(submitApplicationRequest));
		assertEquals("User does not own selected document.", userFriendlyException.getUserFriendlyMessage());
	}
	
	@Test
	void Given_SubmitApplicationRequestWithMissingRequiredDocument_When_SubmitApplication_Then_UserFriendlyExceptionThrown() {
		UserProfile userProfile = new UserProfile();
		when(userProfileRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(userProfile));
		
		JobPost jobPost = new JobPost();
		jobPost.setStatus(JobPost.STATUS.OPEN);
		jobPost.setResumeRequired(true);
		when(jobPostRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(jobPost));
		
		SubmitApplicationRequest submitApplicationRequest = new SubmitApplicationRequest();
		submitApplicationRequest.setJobPostId(1);
		ArrayList<Integer> documentIds = new ArrayList<>();
		submitApplicationRequest.setDocumentIds(documentIds);
		
		UserFriendlyException userFriendlyException = assertThrows(UserFriendlyException.class, () -> applicationController.submitApplication(submitApplicationRequest));
		assertEquals("A resume is required for this application.", userFriendlyException.getUserFriendlyMessage());
	}
	
	@BeforeEach
	void init() {
		MyUserDetails userDetails = Mockito.mock(MyUserDetails.class);
		Authentication authentication = Mockito.mock(Authentication.class);
		Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
	}

	@AfterEach
	void tearDown() {
		SecurityContextHolder.clearContext();
	}
}

