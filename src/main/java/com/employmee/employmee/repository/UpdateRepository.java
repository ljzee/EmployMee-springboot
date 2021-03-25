package com.employmee.employmee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.employmee.employmee.entity.Update;

public interface UpdateRepository extends JpaRepository<Update, Integer> {
	
	@Query("SELECT businessUpdate \n"
		 + "FROM Update businessUpdate \n"
		 + "WHERE businessUpdate.businessProfile.user.id = :businessProfileId")
	public List<Update> getUpdatesByBusinessProfileId(int businessProfileId);
}
