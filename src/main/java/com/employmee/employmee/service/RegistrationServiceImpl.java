package com.employmee.employmee.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.employmee.employmee.entity.User;
import com.employmee.employmee.exception.UserAlreadyExistException;
import com.employmee.employmee.payload.request.RegisterRequest;
import com.employmee.employmee.repository.UserRepository;

@Service
public class RegistrationServiceImpl implements RegistrationService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Override
	public User processRegistration(RegisterRequest request) {
		// check if email/username is already taken
		Boolean emailExists = userRepository.existsByEmail(request.getEmail());
		Boolean usernameExists = userRepository.existsByUsername(request.getUsername());
		if(emailExists || usernameExists) {
			throw new UserAlreadyExistException("Email or username has already been taken.");
		}
		
		// encode the password
		String encodedPassword = encoder.encode(request.getPassword());
		User newUser = new User(request.getUsername(), request.getEmail(), encodedPassword, User.ROLE.valueOf(request.getUserType()));
		
		return userRepository.save(newUser);
	}

}
