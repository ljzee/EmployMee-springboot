package com.employmee.employmee.payload.request;

import java.time.LocalDate;

import javax.validation.constraints.Future;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UpdateJobPostDeadlineRequest {
	@Future(message = "Deadline must be in the future.")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate deadline;

	public LocalDate getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}
	
}
