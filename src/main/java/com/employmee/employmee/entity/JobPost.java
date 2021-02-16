package com.employmee.employmee.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
@Table(name = "job_posts")
public class JobPost {
	
	public enum STATUS {
		OPEN,
		CLOSED,
		DRAFT
	}
	
	public enum TYPE {
		FULLTIME,
		PARTIME,
		TEMPORARY,
		INTERNSHIP
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "duration")
	private String duration;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "position_type")
	private TYPE positionType;
	
	@Column(name = "openings")
	private int openings;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "salary")
	private String salary;
	
	@Column(name = "deadline")
	private LocalDate deadline;
	
	@Column(name = "coverletter_required")
	private boolean coverletterRequired;
	
	@Column(name = "resume_required")
	private boolean resumeRequired;
	
	@Column(name = "other_required")
	private boolean otherRequired;
	
	@Column(name = "date_created")
	private LocalDate dateCreated;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private STATUS status;
	
	@Column(name = "date_published")
	private LocalDate datePublished;
	
	@Column(name="uuid", unique=true, nullable=false, updatable=false)
	@Type(type="pg-uuid")
	private java.util.UUID uuid = java.util.UUID.randomUUID();
	
	@ManyToOne(fetch = FetchType.LAZY)
	private BusinessProfile businessProfile;
	
	@ManyToMany()
	@JoinTable(name = "job_addresses",
	           joinColumns=@JoinColumn(name="job_post_id"),
	           inverseJoinColumns=@JoinColumn(name="address_id"))
	Set<Address> addresses = new HashSet<>();
	
	public JobPost() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public TYPE getPositionType() {
		return positionType;
	}

	public void setPositionType(TYPE positionType) {
		this.positionType = positionType;
	}

	public int getOpenings() {
		return openings;
	}

	public void setOpenings(int openings) {
		this.openings = openings;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public LocalDate getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}

	public boolean isCoverletterRequired() {
		return coverletterRequired;
	}

	public void setCoverletterRequired(boolean coverletterRequired) {
		this.coverletterRequired = coverletterRequired;
	}

	public boolean isResumeRequired() {
		return resumeRequired;
	}

	public void setResumeRequired(boolean resumeRequired) {
		this.resumeRequired = resumeRequired;
	}

	public boolean isOtherRequired() {
		return otherRequired;
	}

	public void setOtherRequired(boolean otherRequired) {
		this.otherRequired = otherRequired;
	}

	public LocalDate getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDate dateCreated) {
		this.dateCreated = dateCreated;
	}

	public STATUS getStatus() {
		return status;
	}

	public void setStatus(STATUS status) {
		this.status = status;
	}

	public LocalDate getDatePublished() {
		return datePublished;
	}

	public void setDatePublished(LocalDate datePublished) {
		this.datePublished = datePublished;
	}

	public BusinessProfile getBusinessProfile() {
		return businessProfile;
	}

	public void setBusinessProfile(BusinessProfile businessProfile) {
		this.businessProfile = businessProfile;
	}

	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JobPost other = (JobPost) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}
	
	
}

