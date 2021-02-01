package com.employmee.employmee.payload.response;

public class ApiValidationError extends ApiSubError {
	
	private String field;
	
	private String message;

	public ApiValidationError(String field, String message) {
		// TODO Auto-generated constructor stub
		this.field = field;
		this.message = message;
	}

	public ApiValidationError(String message) {
		// TODO Auto-generated constructor stub
		this.field = null;
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
