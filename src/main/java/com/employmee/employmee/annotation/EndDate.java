package com.employmee.employmee.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.employmee.employmee.validation.EndDateValidator;

@Constraint(validatedBy = EndDateValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EndDate {
	String message() default "End date must be after start date.";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
