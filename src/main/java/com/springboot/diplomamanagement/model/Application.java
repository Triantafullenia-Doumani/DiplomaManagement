package com.springboot.diplomamanagement.model;

import javax.persistence.*;

@Entity
@Table(name="application")
public class Application {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private  Long id;
	
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;


	@Column
	private Double presentationGrade;

	@Column
	private Double implementationGrade;
	@Column
	private Double reportGrade;

	@Column
	private Double totalGrade;

	@Enumerated(EnumType.STRING)
	@Column
	private ApplicationStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "thesis_id")
	private Thesis thesis;
    
	// Constructors
	public Application() {
		super();
	}
	
	public Application(Long id, String fullName) {
		super(); // not sure if needed
		this.id = id;
	}
	
	// Getters and Setters

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Student getStudent() {
		return student;
	}

	public Double getPresentationGrade() {
		return presentationGrade;
	}

	public void setPresentationGrade(Double presentationGrade) {
		this.presentationGrade = presentationGrade;
	}

	public Double getImplementationGrade() {
		return implementationGrade;
	}

	public void setImplementationGrade(Double implementationGrade) {
		this.implementationGrade = implementationGrade;
	}

	public Double getReportGrade() {
		return reportGrade;
	}

	public void setReportGrade(Double reportGrade) {
		this.reportGrade = reportGrade;
	}

	public Subject getSubject() {
		return subject;
	}

	public Double getTotalGrade() {
		return totalGrade;
	}

	public void setTotalGrade(Double totalGrade) {
		this.totalGrade = totalGrade;
	}

	public Thesis getThesis() {
		return thesis;
	}

	public void setThesis(Thesis thesis) {
		this.thesis = thesis;
	}

	public ApplicationStatus getStatus() {
		return status;
	}

	public void setStatus(ApplicationStatus status) {
		this.status = status;
	}
}
