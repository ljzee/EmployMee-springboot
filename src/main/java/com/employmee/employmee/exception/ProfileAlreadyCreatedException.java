package com.employmee.employmee.exception;

public class ProfileAlreadyCreatedException extends RuntimeException {

	int userId;
	
	public ProfileAlreadyCreatedException(int userId) {
		this.userId = userId;
	}

}
