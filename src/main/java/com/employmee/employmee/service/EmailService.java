package com.employmee.employmee.service;

import com.employmee.employmee.entity.Application;

public interface EmailService {
	public void insertSendNewApplicationEmailRequest(Application application);
	
	public void insertSendApplicationStatusUpdatedEmailRequest(Application application);
}
