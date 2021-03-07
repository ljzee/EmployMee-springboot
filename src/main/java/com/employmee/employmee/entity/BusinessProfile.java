package com.employmee.employmee.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "business_profile")
public class BusinessProfile {
	@Id
	@Column(name = "id")
	private int id;
	
	@OneToOne(cascade = CascadeType.ALL, optional = true)
	@JoinColumn(name = "id")
	@MapsId
	private User user;
	
	@Column(name = "company_name")
	private String companyName;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "website")
	private String website;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "profile_image")
	private String profileImage;
	
	@OneToMany(
		mappedBy = "businessProfile",
		cascade = CascadeType.ALL,
		orphanRemoval = true
	)
	Set<JobPost> jobPosts = new HashSet<>();
	
	@ManyToMany(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE
	})
	@JoinTable(name = "business_addresses",
       joinColumns=@JoinColumn(name="business_profile_id"),
       inverseJoinColumns=@JoinColumn(name="address_id"))
	Set<Address> addresses = new HashSet<>();
	
	public BusinessProfile() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}
	
	public Set<JobPost> getJobPosts() {
		return jobPosts;
	}

	public void setJobPosts(Set<JobPost> jobPosts) {
		this.jobPosts = jobPosts;
	}
	
	public void addJobPost(JobPost jobPost) {
		jobPosts.add(jobPost);
		jobPost.setBusinessProfile(this);
	}
	
	public void removeJobPost(JobPost jobPost) {
		jobPosts.remove(jobPost);
		jobPost.setBusinessProfile(null);
	}

	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}
	
	public void addAddress(Address address) {
		addresses.add(address);
	}
	
}
