package com.springboot.diplomamanagement.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


@Entity
@Table(name="professor")
public class Professor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="full_name")
	private String fullName;
	
	@Column(name="speciality")
	private String specialty;

	@OneToOne
	private User user;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subject> subjects = new ArrayList<>();

    @OneToMany(mappedBy = "professor")
    private List<Thesis> thesis = new ArrayList<>();
	/*
Professor has a list of Subject objects that correspond to available diploma thesis subjects. To ease the
management of Subject objects in the database this association can be bidirectional in the sense that a
Subject object is characterized by the supervisor and a list of Application objects that correspond to the
applications of students who are willing to take over the subject
	 */

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

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

	public List<Thesis> getThesis() {
		return thesis;
	}

	public void setThesis(List<Thesis> thesis) {
		this.thesis = thesis;
	}
}