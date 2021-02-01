package com.employmee.employmee.exception;

public class UserNotFoundException extends RuntimeException {

	private int userId;
	
	public UserNotFoundException(int userId) {
		this.setUserId(userId);
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
