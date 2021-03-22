package com.employmee.employmee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employmee.employmee.entity.Experience;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Integer> {

}
