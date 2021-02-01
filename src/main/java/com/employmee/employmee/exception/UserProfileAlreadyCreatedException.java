package com.employmee.employmee.exception;

public class UserProfileAlreadyCreatedException extends RuntimeException {

	int userId;
	
	public UserProfileAlreadyCreatedException(int userId) {
		this.userId = userId;
	}

}
