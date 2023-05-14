package com.springboot.diplomamanagement.service;

import java.util.List;

import com.springboot.diplomamanagement.model.Application;
import com.springboot.diplomamanagement.model.Professor;
import com.springboot.diplomamanagement.model.Subject;
import com.springboot.diplomamanagement.model.Thesis;

public interface ProfessorService {
	
	public Professor retrieveProfile(String fullName);
	public void saveProfile(Professor professor);
	public List<Subject> listProfessorSubjects(String fullName);
	public void addSubject(String kati , Subject subject);

	public void addThesis(String kati , Thesis thesis);
	public List<Application> listApplications(String kati ,int katti );
	public List<Application> listAcceptedApplications(String kati);
	public List<Thesis> listProfessorTheses(String fullName);
	public void assignSubject(String kati, int katti );

	public void deleteThesis(int id);
	public void deleteSubject(int id);

	public void assignGrade(Application application);

}

