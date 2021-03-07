package com.employmee.employmee.payload.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

public class ApiErrorResponse {
	
	private HttpStatus status;
	
	private String message;
	
	private List<ApiSubError> subErrors;
	
	public ApiErrorResponse(HttpStatus status) {
		this.status = status;
	}
	
	public HttpStatus getStatus() {
		return status;
	}
	
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public List<ApiSubError> getSubErrors() {
		return subErrors;
	}
	
	public void setSubErrors(List<ApiSubError> subErrors) {
		this.subErrors = subErrors;
	}
	
    private void addSubError(ApiSubError subError) {
        if (subErrors == null) {
            subErrors = new ArrayList<>();
        }
        subErrors.add(subError);
    }
	
    public void addValidationError(String field, String message) {
        this.addSubError(new ApiValidationError(field, message));
    }

    private void addValidationError(String message) {
    	this.addSubError(new ApiValidationError(message));
    }
    
    private void addValidationError(ObjectError objectError) {
    	this.addValidationError(objectError.getDefaultMessage());
    }
    
    private void addValidationError(FieldError fieldError) {
        this.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
    }

    public void addFieldValidationErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addValidationError);
    }
    
    public void addGlobalValidationErrors(List<ObjectError> objectErrors) {
    	objectErrors.forEach(this::addValidationError);
    }
}
