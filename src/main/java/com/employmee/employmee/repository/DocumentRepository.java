package com.employmee.employmee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employmee.employmee.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Integer> {

}
