package com.springboot.diplomamanagement.model.strategies;

import com.springboot.diplomamanagement.model.Application;

public class FewestCoursesStrategy extends TemplateStrategyAlgorithm{
	
	public int compareApplications(Application app1,Application app2) {
		if (app1.getStudent().getCoursesLeftForGraduation() < app2.getStudent().getCoursesLeftForGraduation()) {
			return 1;
		} else if (app1.getStudent().getCoursesLeftForGraduation() == app2.getStudent().getCoursesLeftForGraduation()) {
			return 0;
		} else {
			return -1;
		}
	}
}