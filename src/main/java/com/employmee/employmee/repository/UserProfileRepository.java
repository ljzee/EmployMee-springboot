package com.employmee.employmee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employmee.employmee.entity.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {

}
