package com.springboot.diplomamanagement.model.strategies;

import java.util.List;

import com.springboot.diplomamanagement.model.Application;
import com.springboot.diplomamanagement.model.Student;

public abstract class TemplateStrategyAlgorithm implements BestApplicantStrategy{

	public Student findBestApplicant(List<Application> applications) {
		Student bestStudent= null;

		if(applications.size() == 1) {
			return applications.get(0).getStudent();
		}
		for(int i=0;i<applications.size()-1;i++) {
			if(compareApplications(applications.get(i),applications.get(i+1)) == 1 || compareApplications(applications.get(i),applications.get(i+1)) == 0 ) {
				bestStudent= applications.get(i).getStudent();
			}
			else {
				bestStudent= applications.get(i+1).getStudent();
			}
		}

		return bestStudent;
	}

	public abstract int compareApplications(Application app1, Application app2);
	
	
}