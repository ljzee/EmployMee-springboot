package com.employmee.employmee.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.employmee.employmee.payload.response.ApiErrorResponse;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    
	@Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST);
        apiErrorResponse.setMessage("Validation error");
        apiErrorResponse.addFieldValidationErrors(ex.getBindingResult().getFieldErrors());
        apiErrorResponse.addGlobalValidationErrors(ex.getBindingResult().getGlobalErrors());
        return buildResponseEntity(apiErrorResponse);
    }
    
    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<Object> handleBadCredentialsException(
            BadCredentialsException ex) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.UNAUTHORIZED);
        apiErrorResponse.setMessage("Username or password is incorrect.");
        return buildResponseEntity(apiErrorResponse);
    }
	
    @ExceptionHandler(UserAlreadyExistException.class)
    protected ResponseEntity<Object> handleUserAlreadyExistException(UserAlreadyExistException ex) {
    	ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST);
    	apiErrorResponse.setMessage("Username or email is not available. Please choose a new username and email.");
    	return buildResponseEntity(apiErrorResponse);
    }
    
    @ExceptionHandler(ProfileAlreadyCreatedException.class)
    protected ResponseEntity<Object> handleUserProfileAlreadyCreatedException(ProfileAlreadyCreatedException ex) {
    	ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST);
    	apiErrorResponse.setMessage("A profile has already been created.");
    	return buildResponseEntity(apiErrorResponse);
    }
    
    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
    	ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR);
    	apiErrorResponse.setMessage("Internal server error.");
    	return buildResponseEntity(apiErrorResponse);
    }
    
    @ExceptionHandler(UserFriendlyException.class)
    protected ResponseEntity<Object> handleUserFriendlyException(UserFriendlyException ex) {
    	ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST);
    	apiErrorResponse.setMessage(ex.getUserFriendlyMessage());
    	return buildResponseEntity(apiErrorResponse);
    }
    
    private ResponseEntity<Object> buildResponseEntity(ApiErrorResponse apiErrorResponse) {
        return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.getStatus());
    }
}
