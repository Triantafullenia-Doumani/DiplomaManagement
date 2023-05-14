package com.springboot.diplomamanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.diplomamanagement.model.Subject;

import java.util.Optional;

@Repository
public interface SubjectDAO extends JpaRepository<Subject, Integer>{

    Optional<Subject> findBySubjectName(String name);
}