package com.employmee.employmee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employmee.employmee.entity.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

}
