package com.employmee.employmee.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "send_email_requests")
public class SendEmailRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "fromEmail")
	private String fromEmail;
	
	@Column(name = "toEmail")
	private String toEmail;
	
	@Column(name = "subject")
	private String subject;
	
	@Column(name = "body")
	private String body;
	
	@Column(name = "has_attempted")
	private boolean hasAttempted;
	
	@Column(name = "attempted_timestamp")
	private LocalDateTime attemptedTimestamp;
	
	public SendEmailRequest() {}
	
	public SendEmailRequest(String fromEmail, String toEmail, String subject, String body) {
		this.fromEmail = fromEmail;
		this.toEmail = toEmail;
		this.subject = subject;
		this.body = body;
		this.hasAttempted = false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFromEmail() {
		return fromEmail;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public String getToEmail() {
		return toEmail;
	}

	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public boolean isHasAttempted() {
		return hasAttempted;
	}

	public void setHasAttempted(boolean hasAttempted) {
		this.hasAttempted = hasAttempted;
	}

	public LocalDateTime getAttemptedTimestamp() {
		return attemptedTimestamp;
	}

	public void setAttemptedTimestamp(LocalDateTime attemptedTimestamp) {
		this.attemptedTimestamp = attemptedTimestamp;
	}
	
	
}
