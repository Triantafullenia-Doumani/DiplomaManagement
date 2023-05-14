package com.springboot.diplomamanagement.dao;

import com.springboot.diplomamanagement.model.Student;
import com.springboot.diplomamanagement.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.diplomamanagement.model.Application;

import java.util.List;

@Repository
public interface ApplicationDAO extends JpaRepository<Application,Long> { // to long thelei allagh

    List<Application> findBySubject(Subject subject);
    List<Application> findByStudent(Student student);

    List<Application> findBySubject_IsAssigned(boolean assigned);
}
