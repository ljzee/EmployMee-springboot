package com.employmee.employmee.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.employmee.employmee.annotation.EndDate;
import com.employmee.employmee.payload.request.AddExperienceRequest;

// Class to validate that end date is after start date
public class EndDateValidator implements ConstraintValidator<EndDate, AddExperienceRequest> {

	@Override
	public boolean isValid(AddExperienceRequest addExperienceRequest, ConstraintValidatorContext context) {
		// If end date is not provided, then it is valid
		if(addExperienceRequest.getEndDate() == null) {
			return true;
		}
		
		return addExperienceRequest.getEndDate().isAfter(addExperienceRequest.getStartDate());
	}

}
