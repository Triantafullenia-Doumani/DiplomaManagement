package com.springboot.diplomamanagement.service;

import java.util.List;
import java.util.Optional;

import com.springboot.diplomamanagement.model.Subject;

public interface SubjectService {
	
	public void save(Subject subject);

	public void delete(int id);
	public List<Subject> findAll();
	public Optional<Subject> findById(int id);
	public Optional<Subject> findByTitle(String title);
}
