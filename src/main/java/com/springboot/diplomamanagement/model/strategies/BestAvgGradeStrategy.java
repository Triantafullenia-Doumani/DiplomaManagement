package com.springboot.diplomamanagement.model.strategies;

import com.springboot.diplomamanagement.model.Application;

public class BestAvgGradeStrategy extends TemplateStrategyAlgorithm{
	
	public int compareApplications(Application app1,Application app2) {
		// Compare the two applications and return a result
		if (app1.getStudent().getCurrentAvgGrade() < app2.getStudent().getCurrentAvgGrade()) {
			return -1;
		} else if (app1.getStudent().getCurrentAvgGrade() == app2.getStudent().getCurrentAvgGrade()) {
			return 0;
		} else {
			return 1;
		}
	}
}