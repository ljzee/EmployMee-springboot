package com.employmee.employmee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employmee.employmee.entity.BusinessProfile;
import com.employmee.employmee.entity.JobPost;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, Integer> {
	List<JobPost> findByBusinessProfileIdAndStatus(int id, JobPost.STATUS status);
}
