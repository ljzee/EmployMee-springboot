package com.employmee.employmee.entity;

import java.util.HashSet;
import java.util.Iterator;
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
	
	@Column(name = "profile_image")
	private String profileImage;
	
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
	
	@OneToMany(
		mappedBy = "userProfile",
		cascade = CascadeType.ALL,
		orphanRemoval = true
	)
	Set<Document> documents = new HashSet<>();
	
	@OneToMany(
		mappedBy = "userProfile",
		cascade = CascadeType.ALL,
		orphanRemoval = true
	)
	Set<Application> applications = new HashSet<>();
	
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

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
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
	
	public Set<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(Set<Document> documents) {
		this.documents = documents;
	}

	public boolean hasDocument(Document document) {
		return this.documents.contains(document);
	}
	
	public void addDocument(Document document) {
		documents.add(document);
		document.setUserProfile(this);
	}
	
	public void removeDocument(Document document) {
		documents.remove(document);
		document.setUserProfile(null);
	}

	public Set<Application> getApplications() {
		return applications;
	}

	public void setApplications(Set<Application> applications) {
		this.applications = applications;
	}
	
	public void addApplication(Application application) {
		this.applications.add(application);
		application.setUserProfile(this);
	}

	public void removeApplication(Application application) {
		this.applications.remove(application);
		application.setUserProfile(null);
	}
	
	private Set<JobPost> getAppliedToJobPosts() {
		Iterator<Application> applicationIterator = this.applications.iterator();
		Set<JobPost> appliedToJobPosts = new HashSet<>();
		while(applicationIterator.hasNext()) {
			Application application = applicationIterator.next();
			appliedToJobPosts.add(application.jobPost);
		}
		
		return appliedToJobPosts;
	}
	
	public boolean hasAppliedToJobPost(JobPost jobPost) {
		Set<JobPost> appliedToJobPosts = this.getAppliedToJobPosts();
		return appliedToJobPosts.contains(jobPost);
	}
}
