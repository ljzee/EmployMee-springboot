package com.employmee.employmee.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_profile")
public class UserProfile {
	
	@Id
	@Column(name = "id")
	private int id;
	
	@OneToOne(cascade = CascadeType.ALL, optional = true)
	@JoinColumn(name = "id")
	@MapsId
	private User user;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "personal_website")
	private String personalWebsite;
	
	@Column(name = "github_link")
	private String githubLink;
	
	@Column(name = "bio")
	private String bio;
	
	@Column(name = "profile_image_name")
	private String profileImageName;
	
	@ManyToMany(cascade = {
	        CascadeType.PERSIST,
	        CascadeType.MERGE
	})
    @JoinTable(
    		name = "user_bookmarks", 
            joinColumns = { @JoinColumn(name = "user_profile_id") }, 
            inverseJoinColumns = { @JoinColumn(name = "job_post_id") }
    )
	Set<JobPost> bookmarkedJobPosts = new HashSet<>();
	
	public UserProfile() {}

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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPersonalWebsite() {
		return personalWebsite;
	}

	public void setPersonalWebsite(String personalWebsite) {
		this.personalWebsite = personalWebsite;
	}

	public String getGithubLink() {
		return githubLink;
	}

	public void setGithubLink(String githubLink) {
		this.githubLink = githubLink;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getProfileImageName() {
		return profileImageName;
	}

	public void setProfileImageName(String profileImageName) {
		this.profileImageName = profileImageName;
	}

	public Set<JobPost> getBookmarkedJobPosts() {
		return bookmarkedJobPosts;
	}

	public void setBookmarkedJobPosts(Set<JobPost> jobPosts) {
		this.bookmarkedJobPosts = jobPosts;
	}

	public boolean hasBookmarkedJobPost(JobPost jobPost) {
		return this.bookmarkedJobPosts.contains(jobPost);
	}
	
	public boolean hasAppliedToJobPost(JobPost jobPost) {
		return false;
	}
	
}
