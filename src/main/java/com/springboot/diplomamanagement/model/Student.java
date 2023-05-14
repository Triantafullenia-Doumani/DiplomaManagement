package com.springboot.diplomamanagement.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


@Entity
@Table(name="student")
public class Student {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private  int id;
	
	@Column(name="full_name")
	private String fullName;
	
	@Column(name="year_of_studies")
	private int yearOfStudies;
	
	@Column(name="current_avg_grade")
	private float currentAvgGrade;
	
	@Column(name="courses_left_for_graduation")
	private int coursesLeftForGraduation;
	
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Application> applications = new ArrayList<>();

	@OneToOne
	private User user;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getYearOfStudies() {
		return yearOfStudies;
	}

	public void setYearOfStudies(int yearOfStudies) {
		this.yearOfStudies = yearOfStudies;
	}

	public float getCurrentAvgGrade() {
		return currentAvgGrade;
	}

	public void setCurrentAvgGrade(float currentAvgGrade) {
		this.currentAvgGrade = currentAvgGrade;
	}

	public int getCoursesLeftForGraduation() {
		return coursesLeftForGraduation;
	}

	public void setCoursesLeftForGraduation(int coursesLeftForGraduation) {
		this.coursesLeftForGraduation = coursesLeftForGraduation;
	}

	public List<Application> getApplications() {
		return applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}