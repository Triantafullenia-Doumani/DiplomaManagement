package com.springboot.diplomamanagement.model.strategies;

import java.util.List;

import com.springboot.diplomamanagement.model.Application;
import com.springboot.diplomamanagement.model.Student;

public class BestApplicantStrategyFactory implements BestApplicantStrategy {
	
	// Private instance of the factory class
    private static BestApplicantStrategyFactory instance = null;
    
    // Public static method to get the instance of the factory class
	public static BestApplicantStrategyFactory getInstance() {
		if (instance == null) {
			instance = new BestApplicantStrategyFactory();
		}
		return instance;
	}
	
	public BestApplicantStrategy createStrategy(String strategyKATI) {//?? ti tha einai afto...
		if(strategyKATI.equals(Strategy.BEST_GRADE.getValue())) {
			return new BestAvgGradeStrategy();
		}
		else if(strategyKATI.equals(Strategy.MINIMUM_COURSES_REMAINING.getValue())) {
			return new FewestCoursesStrategy();
		}
		return null;
	}
	
	public Student findBestApplicant(List<Application> applications) {
		return null;
	}
}
