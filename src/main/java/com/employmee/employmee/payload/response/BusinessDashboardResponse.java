package com.employmee.employmee.payload.response;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.employmee.employmee.entity.Address;
import com.employmee.employmee.entity.BusinessProfile;
import com.employmee.employmee.entity.JobPost;

public class BusinessDashboardResponse {
	private List<BusinessJobPost> jobs;

	public List<BusinessJobPost> getJobs() {
		return jobs;
	}

	public void setJobs(List<JobPost> businessJobPosts) {
		List<BusinessJobPost> jobs = new ArrayList<>();
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
			
			BusinessJobPost businessJobPost = new BusinessJobPost(jobPostId, jobTitle, jobCity, jobState);
			jobs.add(businessJobPost);
		}
		
		this.jobs = jobs;
	}
}
