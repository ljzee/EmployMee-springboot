package com.employmee.employmee.payload.request;

import javax.validation.constraints.NotBlank;

public class AddUpdateRequest {
	@NotBlank(message = "Content must not be blank.")
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
