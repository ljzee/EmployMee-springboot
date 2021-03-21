package com.employmee.employmee.payload.request;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.employmee.employmee.annotation.EndDate;
import com.fasterxml.jackson.annotation.JsonFormat;

@EndDate // annotation is validate endDate(if provided) is after startDate
public class AddExperienceRequest {
	
	@NotBlank(message = "Company name must not be blank.")
	private String companyName;
	
	@NotBlank(message = "Title must not be blank.")
	private String title;
	
	@NotBlank(message = "Location must not be blank.")
	private String location;
	
	private String description;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "Start date must be provided.")
	private LocalDate startDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDate;

	public AddExperienceRequest() {}
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	
}
