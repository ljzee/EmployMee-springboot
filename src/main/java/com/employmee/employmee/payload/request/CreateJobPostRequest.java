package com.employmee.employmee.payload.request;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.employmee.employmee.annotation.ValueOfEnum;
import com.employmee.employmee.entity.JobPost;
import com.fasterxml.jackson.annotation.JsonFormat;

public class CreateJobPostRequest {
	
	@NotBlank(message = "Title must not be blank.")
	private String title;
	
	@NotBlank(message = "Duration must not be blank.")
	private String duration;
	
	@ValueOfEnum(enumClass = JobPost.TYPE.class, message="Position type must be one of FULLTIME, PARTTIME, TEMPORARY, or INTERNSHIP.")
	private String positionType;
	
	private int addressId;
	
	@PositiveOrZero(message = "Openings must be a number greater or equal to 0.")
	private int openings;
	
	private boolean resumeRequired;
	
	private boolean coverletterRequired;
	
	private boolean otherRequired;
	
	@ValueOfEnum(enumClass = JobPost.STATUS.class, message="Status must be one of OPEN, CLOSED, or DRAFT.")
	private String status;
	
	private String description;
	
	private String salary;
	
	@Future(message = "Deadline must be in the future.")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate deadline;
	
	public CreateJobPostRequest() {}

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

	public String getPositionType() {
		return positionType;
	}

	public void setPositionType(String positionType) {
		this.positionType = positionType;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public int getOpenings() {
		return openings;
	}

	public void setOpenings(int openings) {
		this.openings = openings;
	}

	public boolean isResumeRequired() {
		return resumeRequired;
	}

	public void setResumeRequired(boolean resumeRequired) {
		this.resumeRequired = resumeRequired;
	}

	public boolean isCoverletterRequired() {
		return coverletterRequired;
	}

	public void setCoverletterRequired(boolean coverletterRequired) {
		this.coverletterRequired = coverletterRequired;
	}

	public boolean isOtherRequired() {
		return otherRequired;
	}

	public void setOtherRequired(boolean otherRequired) {
		this.otherRequired = otherRequired;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
	
	
}
