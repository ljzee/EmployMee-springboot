package com.employmee.employmee.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.employmee.employmee.entity.JobPost;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, Integer> {
	List<JobPost> findByBusinessProfileIdAndStatus(int id, JobPost.STATUS status);
	
	@Query("SELECT distinct jobPost \n"
		 + "FROM JobPost jobPost \n"
	     + "	JOIN jobPost.addresses address \n"
	     + "	JOIN jobPost.businessProfile businessProfile \n"
	     + "WHERE address.country LIKE %:country% \n"
	     + "	AND address.state LIKE %:state% \n"
	     + "	AND address.city  LIKE %:city% \n"
	     + "	AND (jobPost.title LIKE %:searchField% OR businessProfile.companyName LIKE %:searchField%)"
	     + "	AND jobPost.status = 'OPEN'")
	Set<JobPost> searchJobPost(@Param("searchField") String searchField, 
			                          @Param("country") String country,
			                          @Param("state") String state,
			                          @Param("city") String city);
	
	Optional<JobPost> findFirstByIdAndStatusIn(int id, List<JobPost.STATUS> statuses);
}
