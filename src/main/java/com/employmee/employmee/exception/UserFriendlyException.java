package com.employmee.employmee.exception;

public class UserFriendlyException extends RuntimeException {
	private String userFriendlyMessage;
	
	public UserFriendlyException(String message) {
		this.userFriendlyMessage = message;
	}

	public String getUserFriendlyMessage() {
		return userFriendlyMessage;
	}

	public void setUserFriendlyMessage(String userFriendlyMessage) {
		this.userFriendlyMessage = userFriendlyMessage;
	}
	
	
}
