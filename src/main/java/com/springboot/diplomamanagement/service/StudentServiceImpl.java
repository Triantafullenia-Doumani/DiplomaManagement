package com.springboot.diplomamanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.springboot.diplomamanagement.dao.ApplicationDAO;
import com.springboot.diplomamanagement.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.diplomamanagement.dao.StudentDAO;
import com.springboot.diplomamanagement.dao.SubjectDAO;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentDAO studentRepository;
	
	@Autowired
	private SubjectDAO subjectRepository;

	@Autowired
	private ApplicationDAO applicationDAO;

	@Autowired
	UserService userService;
	
	@Override
	public void saveProfile(Student theStudent) {
		User userSaved= userService.findByUserName(theStudent.getUser().getUsername()).get();
		theStudent.setUser(userSaved);
		studentRepository.save(theStudent);
	}

	@Override
	public Student retrieveProfile(String full_name) {
		Optional<Student> result = studentRepository.findByUser_Username(full_name);
		
		if (result.isPresent()) {
			return result.get();
		}
		else {
			// we didn't find the student
			throw new RuntimeException("Did not find student name - " + full_name);
		}
	}

	// einai to ekshs Use case?
	// S.2 Have access to a list of available diploma thesis subjects that are offered by the professors.
	@Override
	public List<Subject> listStudentSubjects() {
		return subjectRepository.findAll(); // subjects einai ta diathesima, enw thesis ta kaparwmena
	}

	// S.4 Apply for a diploma thesis subject.
	@Override
	public void applyToSubject(String full_name, int subject_id) {
		Student student = retrieveProfile(full_name);
	    // Find the subject by its ID
		Optional<Subject> subject= subjectRepository.findById(subject_id);

		boolean applicationAlreadySubmitted= student.getApplications().stream().filter(s-> s.getSubject().getId() == subject_id).collect(Collectors.toList()).size() > 0;
		if(applicationAlreadySubmitted) {
			throw new RuntimeException("You have already applied for this subject.");
		}
	    // Optional<Subject> subject = subjectRepository.findById(subject_id);
	    // Create a new application for the student and subject
	    Application application = new Application();
	    application.setStudent(student);
	    application.setSubject(subject.get());
		application.setThesis(subject.get().getThesis());
		application.setImplementationGrade(0.0);
		application.setPresentationGrade(0.0);
		application.setReportGrade(0.0);
		application.setTotalGrade(0.0);
		application.setImplementationGrade(0.0);
		application.setStatus(ApplicationStatus.PENDING);
	    // Add the new application to the student's list of applications
	    student.getApplications().add(application);
	    // Save the changes to the database
	    studentRepository.save(student);
	}

	@Override
	public List<Application> listStudentApplications(String kati) {
		Student student = retrieveProfile(kati);
		return applicationDAO.findByStudent(student);
	}

}