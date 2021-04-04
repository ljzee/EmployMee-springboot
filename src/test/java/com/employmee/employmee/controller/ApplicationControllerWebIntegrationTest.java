package com.employmee.employmee.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.employmee.employmee.entity.Application;
import com.employmee.employmee.entity.Document;
import com.employmee.employmee.entity.JobPost;
import com.employmee.employmee.entity.UserProfile;
import com.employmee.employmee.payload.request.SubmitApplicationRequest;
import com.employmee.employmee.payload.response.ApiErrorResponse;
import com.employmee.employmee.repository.ApplicationRepository;
import com.employmee.employmee.repository.BusinessProfileRepository;
import com.employmee.employmee.repository.DocumentRepository;
import com.employmee.employmee.repository.JobPostRepository;
import com.employmee.employmee.repository.UserProfileRepository;
import com.employmee.employmee.security.JwtAuthenticationEntryPoint;
import com.employmee.employmee.security.JwtUtils;
import com.employmee.employmee.security.MyUserDetails;
import com.employmee.employmee.service.ApplicationService;
import com.employmee.employmee.service.StorageService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = ApplicationController.class)
@AutoConfigureMockMvc(addFilters = false)
class ApplicationControllerWebIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	JobPostRepository jobPostRepository;
	
	@MockBean
	UserProfileRepository userProfileRepository;
	
	@MockBean
	DocumentRepository documentRepository;
	
	@MockBean
	ApplicationRepository applicationRepository;
	
	@MockBean
	BusinessProfileRepository businessProfileRepository;
	
	@MockBean
	UserDetailsService userDetailsService;
	
	@MockBean
	ApplicationService applicationService;
	
	@MockBean
	StorageService storageService;
	
	@MockBean
	JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@MockBean
	JwtUtils jwtUtils;
	
	@Test
	void Given_ValidUrlAndMethodAndContentType_When_SubmitApplication_Then_Return200() throws Exception {
		UserProfile userProfile = new UserProfile();
		when(userProfileRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(userProfile));
		
		JobPost jobPost = new JobPost();
		jobPost.setStatus(JobPost.STATUS.OPEN);
		when(jobPostRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(jobPost));
		
		SubmitApplicationRequest submitApplicationRequest = new SubmitApplicationRequest();
		submitApplicationRequest.setJobPostId(1);
		ArrayList<Integer> documentIds = new ArrayList<>();
		submitApplicationRequest.setDocumentIds(documentIds);
		
		mockMvc.perform(post("/application").contentType("application/json").content(objectMapper.writeValueAsString(submitApplicationRequest))).andExpect(status().isOk());
	}
	
	@Test
	void Given_SubmitApplicationRequestWithNullJobPostId_When_SubmitApplication_Then_Return400AndApiErrorResponse() throws Exception {
		// Given
		UserProfile userProfile = new UserProfile();
		when(userProfileRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(userProfile));
		
		JobPost jobPost = new JobPost();
		jobPost.setStatus(JobPost.STATUS.OPEN);
		when(jobPostRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(jobPost));
		
		SubmitApplicationRequest submitApplicationRequest = new SubmitApplicationRequest();
		
		// When
		MvcResult mvcResult = mockMvc.perform(post("/application")
				                             .contentType("application/json")
				                             .content(objectMapper.writeValueAsString(submitApplicationRequest)))
									 .andExpect(status().isBadRequest())
									 .andReturn();
		
		// Then
		ApiErrorResponse expectedApiErrorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST, "Validation error");
		expectedApiErrorResponse.addValidationError("jobPostId", "Job post ID must not be empty.");
		
		String expectedApiErrorResponseString = objectMapper.writeValueAsString(expectedApiErrorResponse);
		String actualApiErrorResponseString = mvcResult.getResponse().getContentAsString();
		assertThat(actualApiErrorResponseString).isEqualToIgnoringWhitespace(expectedApiErrorResponseString);
	}
	
	@Test
	void Given_SubmitApplicationRequestWithNonExistentJobPostId_When_SubmitApplication_Then_Return400AndApiErrorResponse() throws Exception {
		// Given
		when(jobPostRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		
		SubmitApplicationRequest submitApplicationRequest = new SubmitApplicationRequest();
		submitApplicationRequest.setJobPostId(1);
		
		// When
		MvcResult mvcResult = mockMvc.perform(post("/application")
				                             .contentType("application/json")
				                             .content(objectMapper.writeValueAsString(submitApplicationRequest)))
									 .andExpect(status().isBadRequest())
									 .andReturn();
		
		// Then
		ApiErrorResponse expectedApiErrorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST, "Job post does not exist.");
		
		String expectedApiErrorResponseString = objectMapper.writeValueAsString(expectedApiErrorResponse);
		String actualApiErrorResponseString = mvcResult.getResponse().getContentAsString();
		assertThat(actualApiErrorResponseString).isEqualToIgnoringWhitespace(expectedApiErrorResponseString);
	}
	
	@Test
	void Given_JobPostNotAcceptingApplications_When_SubmitApplication_Then_Return400AndApiErrorResponse() throws Exception {
		// Given
		JobPost jobPost = new JobPost();
		jobPost.setStatus(JobPost.STATUS.CLOSED);
		
		when(jobPostRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(jobPost));
		
		SubmitApplicationRequest submitApplicationRequest = new SubmitApplicationRequest();
		submitApplicationRequest.setJobPostId(1);
	
		// When
		MvcResult mvcResult = mockMvc.perform(post("/application")
				                             .contentType("application/json")
				                             .content(objectMapper.writeValueAsString(submitApplicationRequest)))
									 .andExpect(status().isBadRequest())
									 .andReturn();
		
		// Then
		ApiErrorResponse expectedApiErrorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST, "Job post is currently not accepting applications.");
		
		String expectedApiErrorResponseString = objectMapper.writeValueAsString(expectedApiErrorResponse);
		String actualApiErrorResponseString = mvcResult.getResponse().getContentAsString();
		assertThat(actualApiErrorResponseString).isEqualToIgnoringWhitespace(expectedApiErrorResponseString);
	}
	
	@Test
	void Given_SubmitApplicationRequestWithAlreadyAppliedJobPostId_When_SubmitApplication_Then_Return400AndApiErrorResponse() throws Exception {
		// Given
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
		
		// When
		MvcResult mvcResult = mockMvc.perform(post("/application")
				                             .contentType("application/json")
				                             .content(objectMapper.writeValueAsString(submitApplicationRequest)))
									 .andExpect(status().isBadRequest())
									 .andReturn();
		
		// Then
		ApiErrorResponse expectedApiErrorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST, "An application has already been submitted to this job post.");
		
		String expectedApiErrorResponseString = objectMapper.writeValueAsString(expectedApiErrorResponse);
		String actualApiErrorResponseString = mvcResult.getResponse().getContentAsString();
		assertThat(actualApiErrorResponseString).isEqualToIgnoringWhitespace(expectedApiErrorResponseString);
	}
	
	@Test
	void Given_SubmitApplicationRequestWithNonExistentDocumentId_When_SubmitApplication_Then_Return400AndApiErrorResponse() throws Exception {
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
		
		// When
		MvcResult mvcResult = mockMvc.perform(post("/application")
				                             .contentType("application/json")
				                             .content(objectMapper.writeValueAsString(submitApplicationRequest)))
									 .andExpect(status().isBadRequest())
									 .andReturn();
		
		// Then
		ApiErrorResponse expectedApiErrorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST, "User does not own selected document.");
		
		String expectedApiErrorResponseString = objectMapper.writeValueAsString(expectedApiErrorResponse);
		String actualApiErrorResponseString = mvcResult.getResponse().getContentAsString();
		assertThat(actualApiErrorResponseString).isEqualToIgnoringWhitespace(expectedApiErrorResponseString);
	}
	
	@Test
	void Given_SubmitApplicationRequestWithDocumentIdNotOwnedByUser_When_SubmitApplication_Then_Return400AndApiErrorResponse() throws Exception {
		// Given
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
		
		// When
		MvcResult mvcResult = mockMvc.perform(post("/application")
				                             .contentType("application/json")
				                             .content(objectMapper.writeValueAsString(submitApplicationRequest)))
									 .andExpect(status().isBadRequest())
									 .andReturn();
		
		// Then
		ApiErrorResponse expectedApiErrorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST, "User does not own selected document.");
		
		String expectedApiErrorResponseString = objectMapper.writeValueAsString(expectedApiErrorResponse);
		String actualApiErrorResponseString = mvcResult.getResponse().getContentAsString();
		assertThat(actualApiErrorResponseString).isEqualToIgnoringWhitespace(expectedApiErrorResponseString);
	}
	
	@Test
	void Given_SubmitApplicationRequestWithMissingRequiredDocument_When_SubmitApplication_Then_Return400AndApiErrorResponse() throws Exception{
		// Given
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
		
		// When
		MvcResult mvcResult = mockMvc.perform(post("/application")
				                             .contentType("application/json")
				                             .content(objectMapper.writeValueAsString(submitApplicationRequest)))
									 .andExpect(status().isBadRequest())
									 .andReturn();
		
		// Then
		ApiErrorResponse expectedApiErrorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST, "A resume is required for this application.");
		
		String expectedApiErrorResponseString = objectMapper.writeValueAsString(expectedApiErrorResponse);
		String actualApiErrorResponseString = mvcResult.getResponse().getContentAsString();
		assertThat(actualApiErrorResponseString).isEqualToIgnoringWhitespace(expectedApiErrorResponseString);
	}
	
	@BeforeEach
	void init() {
		// Mock away Spring Security details
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
