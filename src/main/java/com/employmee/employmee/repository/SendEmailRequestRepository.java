package com.employmee.employmee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employmee.employmee.entity.SendEmailRequest;

public interface SendEmailRequestRepository extends JpaRepository<SendEmailRequest, Integer> {
	List<SendEmailRequest> findByHasAttemptedFalse();
}
