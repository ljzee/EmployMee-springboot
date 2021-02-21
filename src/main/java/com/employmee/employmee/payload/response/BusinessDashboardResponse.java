package com.employmee.employmee.payload.response;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.employmee.employmee.entity.Address;
import com.employmee.employmee.entity.JobPost;

public class BusinessDashboardResponse {
	private List<BusinessJobPost> jobs;

	public List<BusinessJobPost> getJobs() {
		return jobs;
	}

	public void setJobs(List<JobPost> businessJobPosts) {
		List<BusinessJobPost> jobs = new ArrayList<>();
		for(JobPost jobPost : businessJobPosts) {
			jobs.add(new BusinessJobPost(jobPost));
		}
		
		this.jobs = jobs;
	}
}
