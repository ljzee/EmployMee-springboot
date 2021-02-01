package com.employmee.employmee.service;

import com.employmee.employmee.entity.User;
import com.employmee.employmee.payload.request.RegisterRequest;

public interface RegistrationService {
	public User processRegistration(RegisterRequest request);
}
