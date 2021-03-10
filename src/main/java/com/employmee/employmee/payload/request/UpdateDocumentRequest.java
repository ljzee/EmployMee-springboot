package com.employmee.employmee.payload.request;

import javax.validation.constraints.NotBlank;

public class UpdateDocumentRequest {
	
	@NotBlank(message = "Name must not be blank.")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
