package com.employmee.employmee.payload.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.employmee.employmee.entity.BusinessProfile;
import com.employmee.employmee.entity.JobPost;

public class DashboardResponse {
	
	private List<Bookmark> bookmarks;
	
	public void setBookmarks(Set<JobPost> bookmarkedJobPosts) {
		List<Bookmark> bookmarks = new ArrayList<>();
		for(JobPost jobPost : bookmarkedJobPosts) {
			BusinessProfile businessProfile = jobPost.getBusinessProfile();
			
			Bookmark bookmark = new Bookmark(jobPost.getId(), 
					                         jobPost.getTitle(), 
					                         businessProfile.getId(),
					                         businessProfile.getCompanyName());
			bookmarks.add(bookmark);
		}
		
		this.bookmarks = bookmarks;
	}
	
	public List<Bookmark> getBookmarks() {
		return this.bookmarks;
	}

}
