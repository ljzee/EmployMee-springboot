package com.employmee.employmee.payload.request;

import javax.validation.constraints.NotBlank;

public class UpdateProfileImageRequest {
	
	@NotBlank(message = "Encoded string must not be blank.")
	private String encodedString;
	
	public UpdateProfileImageRequest() {}

	public String getEncodedString() {
		return encodedString;
	}

	public void setEncodedString(String encodedString) {
		this.encodedString = encodedString;
	}
}
