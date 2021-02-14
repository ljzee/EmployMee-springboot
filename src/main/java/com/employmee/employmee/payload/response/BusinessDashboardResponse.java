package com.employmee.employmee.payload.response;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.employmee.employmee.entity.Address;
import com.employmee.employmee.entity.BusinessProfile;
import com.employmee.employmee.entity.JobPost;

public class BusinessDashboardResponse {
	private List<DashboardJobPost> jobs;

	public List<DashboardJobPost> getJobs() {
		return jobs;
	}

	public void setJobs(Set<JobPost> businessJobPosts) {
		List<DashboardJobPost> jobs = new ArrayList<>();
		for(JobPost jobPost : businessJobPosts) {
			BusinessProfile businessProfile = jobPost.getBusinessProfile();
			
			int jobPostId = jobPost.getId();
			String jobTitle = jobPost.getTitle();
			String jobCity = "";
			String jobState = "";
			Set<Address> jobAddresses = jobPost.getAddresses();
			Iterator<Address> jobAddressesIterator = jobAddresses.iterator();
			if(jobAddressesIterator.hasNext()) {
				Address jobAddress = jobAddressesIterator.next();
				jobCity = jobAddress.getCity();
				jobState = jobAddress.getState();
			}
			
			DashboardJobPost dashboardJobPost = new DashboardJobPost(jobPostId, jobTitle, jobCity, jobState);
			jobs.add(dashboardJobPost);
		}
		
		this.jobs = jobs;
	}
}
