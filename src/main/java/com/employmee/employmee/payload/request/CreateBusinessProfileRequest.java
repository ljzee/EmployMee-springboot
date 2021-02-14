package com.employmee.employmee.payload.request;

import javax.validation.constraints.NotBlank;

public class CreateBusinessProfileRequest {
	@NotBlank(message = "Company name must not be blank.")
	private String companyName;
	
	@NotBlank(message = "Country must not be blank.")
	private String country;
	
	@NotBlank(message = "State must not be blank.")
	private String state;
	
	@NotBlank(message = "City must not be blank.")
	private String city;
	
	@NotBlank(message = "Street address must not be blank.")
	private String streetAddress;
	
	@NotBlank(message = "Postal code must not be blank.")
	private String postalCode;
	
	private String phoneNumber;
	
	private String website;
	
	private String description;
	
	public CreateBusinessProfileRequest() {}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
