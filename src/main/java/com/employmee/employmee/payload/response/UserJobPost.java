package com.employmee.employmee.payload.response;

public class UserJobPost extends JobPost {

	private boolean bookmarked = false;
	
	private boolean applied = false;
	
	public UserJobPost(com.employmee.employmee.entity.JobPost jobPost) {
		super(jobPost);
		
	}

	public boolean isBookmarked() {
		return bookmarked;
	}

	public void setBookmarked(boolean bookmarked) {
		this.bookmarked = bookmarked;
	}

	public boolean isApplied() {
		return applied;
	}

	public void setApplied(boolean applied) {
		this.applied = applied;
	}
	
}
