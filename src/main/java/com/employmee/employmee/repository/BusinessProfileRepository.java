package com.employmee.employmee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employmee.employmee.entity.BusinessProfile;

@Repository
public interface BusinessProfileRepository extends JpaRepository<BusinessProfile, Integer> {

}
