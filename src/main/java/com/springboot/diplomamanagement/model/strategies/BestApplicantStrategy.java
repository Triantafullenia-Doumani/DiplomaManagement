package com.springboot.diplomamanagement.model.strategies;

import java.util.List;

import com.springboot.diplomamanagement.model.Application;
import com.springboot.diplomamanagement.model.Student;

public interface BestApplicantStrategy {
	
	public Student findBestApplicant(List<Application> applications);
		
}