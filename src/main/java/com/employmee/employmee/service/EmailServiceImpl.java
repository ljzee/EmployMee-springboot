package com.employmee.employmee.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.employmee.employmee.entity.Application;
import com.employmee.employmee.entity.BusinessProfile;
import com.employmee.employmee.entity.JobPost;
import com.employmee.employmee.entity.SendEmailRequest;
import com.employmee.employmee.entity.UserProfile;
import com.employmee.employmee.repository.SendEmailRequestRepository;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender emailSender;
	
	@Autowired
	private SpringTemplateEngine templateEngine;
	
	@Autowired
	private SendEmailRequestRepository sendEmailRequestRepository;
	
	@Value("${spring.mail.username}")
    private String username;
	
	@Override
	public void insertSendNewApplicationEmailRequest(Application application) {
		JobPost jobPost = application.getJobPost();
		
		String subjectTemplate = "New Application - %s";
		String subject = String.format(subjectTemplate, application.getJobPost().getTitle());
		
		Context context = new Context();
		context.setVariable("jobPostTitle", jobPost.getTitle());
		String body = templateEngine.process("mail/new-application.html", context);
		
		BusinessProfile businessProfile = application.getJobPost().getBusinessProfile();
		
		SendEmailRequest sendEmailRequest = new SendEmailRequest(username, businessProfile.getUser().getEmail(), subject, body);
		sendEmailRequestRepository.save(sendEmailRequest);
	}

	@Override
	public void insertSendApplicationStatusUpdatedEmailRequest(Application application) {
		JobPost jobPost = application.getJobPost();
		
		String subjectTemplate = "Application Status Updated - %s (%s)";
		String jobPostTitle = jobPost.getTitle();
		String companyName = jobPost.getBusinessProfile().getCompanyName();
		String subject = String.format(subjectTemplate, jobPostTitle, companyName);
		
		Context context = new Context();
		context.setVariable("jobPostTitle", jobPostTitle);
		context.setVariable("companyName", companyName);
		String body = templateEngine.process("mail/application-status-updated.html", context);
		
		UserProfile userProfile = application.getUserProfile();
		
		SendEmailRequest sendEmailRequest = new SendEmailRequest(username, userProfile.getUser().getEmail(), subject, body);
		sendEmailRequestRepository.save(sendEmailRequest);
	}
	
	@Scheduled(cron = "*/20 * * * * *")
	@Transactional
	public void sendNewApplicationEmails() throws MessagingException {		
		List<SendEmailRequest> pendingRequests = sendEmailRequestRepository.findByHasAttemptedFalse();
		
		for(SendEmailRequest request : pendingRequests) {
			final MimeMessage mimeMessage = emailSender.createMimeMessage();
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, false, "UTF-8");
			
			message.setFrom(request.getFromEmail());
			message.setTo(request.getToEmail());
			message.setSubject(request.getSubject());
			message.setText(request.getBody(), true);
						
			emailSender.send(mimeMessage);
			
			request.setHasAttempted(true);
			request.setAttemptedTimestamp(LocalDateTime.now());
			sendEmailRequestRepository.save(request);
		}
	}

}
