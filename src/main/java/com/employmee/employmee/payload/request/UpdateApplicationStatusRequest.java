package com.employmee.employmee.payload.request;

import com.employmee.employmee.annotation.ValueOfEnum;
import com.employmee.employmee.entity.Application;

public class UpdateApplicationStatusRequest {
	@ValueOfEnum(enumClass = Application.STATUS.class, message = "Status must be one of NEW, ACCEPTED, SAVED, or REJECTED.")
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
