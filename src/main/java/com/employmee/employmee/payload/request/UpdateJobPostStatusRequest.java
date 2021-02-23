package com.employmee.employmee.payload.request;

import com.employmee.employmee.annotation.ValueOfEnum;
import com.employmee.employmee.entity.JobPost;

public class UpdateJobPostStatusRequest {
	@ValueOfEnum(enumClass = JobPost.STATUS.class, message="Status must be one of OPEN or CLOSED")
	private String status = JobPost.STATUS.DRAFT.name();

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
