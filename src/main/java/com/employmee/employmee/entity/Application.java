package com.employmee.employmee.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "applications")
public class Application {
	public enum STATUS {
		NEW,
		ACCEPTED,
		SAVED,
		REJECTED
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private STATUS status;
	
	@Column(name = "date_submitted")
	private LocalDate dateSubmitted;
	
	@Column(name = "date_processed")
	private LocalDate dateProcessed;
	
	@ManyToOne(fetch = FetchType.LAZY)
	UserProfile userProfile;
	
	@ManyToOne(fetch = FetchType.LAZY)
	JobPost jobPost;
	
	@ManyToMany(cascade = {
	        CascadeType.PERSIST,
	        CascadeType.MERGE
	})
	@JoinTable(name = "application_documents",
			   joinColumns= { @JoinColumn(name="application_id") },
			   inverseJoinColumns= { @JoinColumn(name="document_id") })
	Set<Document> documents = new HashSet<>();

	@Column(name="uuid", unique=true, nullable=false, updatable=false)
	@Type(type="pg-uuid")
	private java.util.UUID uuid = java.util.UUID.randomUUID();
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public STATUS getStatus() {
		return status;
	}

	public void setStatus(STATUS status) {
		this.status = status;
	}

	public LocalDate getDateSubmitted() {
		return dateSubmitted;
	}

	public void setDateSubmitted(LocalDate dateSubmitted) {
		this.dateSubmitted = dateSubmitted;
	}

	public LocalDate getDateProcessed() {
		return dateProcessed;
	}

	public void setDateProcessed(LocalDate dateProcessed) {
		this.dateProcessed = dateProcessed;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public JobPost getJobPost() {
		return jobPost;
	}

	public void setJobPost(JobPost jobPost) {
		this.jobPost = jobPost;
	}

	public Set<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(Set<Document> documents) {
		this.documents = documents;
	}
	
	
}
