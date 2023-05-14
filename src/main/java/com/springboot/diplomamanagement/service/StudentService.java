package com.springboot.diplomamanagement.service;

import java.util.List;

import com.springboot.diplomamanagement.model.Application;
import com.springboot.diplomamanagement.model.Student;
import com.springboot.diplomamanagement.model.Subject;

public interface StudentService {
	
	public void saveProfile(Student student);
	public Student retrieveProfile(String fullname);
	public List<Subject> listStudentSubjects();
	public void applyToSubject(String kati,int katiallo);

	List<Application> listStudentApplications(String kati);
	
}