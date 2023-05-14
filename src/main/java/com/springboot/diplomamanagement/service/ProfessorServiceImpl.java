package com.springboot.diplomamanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.springboot.diplomamanagement.dao.*;
import com.springboot.diplomamanagement.model.*;
import com.springboot.diplomamanagement.model.strategies.BestApplicantStrategy;
import com.springboot.diplomamanagement.model.strategies.BestApplicantStrategyFactory;
import com.springboot.diplomamanagement.model.strategies.Strategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfessorServiceImpl implements ProfessorService {

	@Autowired
	private ProfessorDAO professorRepository;

	@Autowired
	private StudentDAO studentRepository;


	@Autowired
	private SubjectService subjectService;


	@Autowired
	private ApplicationDAO applicationRepository;

	@Autowired
	private ThesisDAO thesisRepository;

	@Autowired
	private UserService userService;


	public ProfessorServiceImpl(ProfessorDAO professorRepository,ApplicationDAO applicationRepository,ThesisDAO thesisRepository,StudentDAO studentRepository,UserService userService) {
		this.studentRepository = studentRepository;
		this.applicationRepository=applicationRepository;
		this.thesisRepository= thesisRepository;
		this.professorRepository = professorRepository;
		this.userService= userService;
	}
	@Override
	public Professor retrieveProfile(String full_name) {
		Optional<Professor> result = professorRepository.findByUser_Username(full_name);
		if (result.isPresent() ) {
			return result.get();
		}
		else {
			// we didn't find the professor
			throw new RuntimeException("Did not find professor name - " + full_name);
		}
	}
	@Override
	public void saveProfile(Professor theProfessor) {
		User userSaved= userService.findByUserName(theProfessor.getUser().getUsername()).get();
		theProfessor.setUser(userSaved);
		professorRepository.save(theProfessor);
	}
	
	// P.2 Have access to the list of the available diploma thesis subjects that the professor offers.
	@Override
	public List<Subject> listProfessorSubjects(String full_name) {
		Professor professor = retrieveProfile(full_name);
		return professor.getSubjects(); 
	}

	// to use case  P3 ennoei na prosthesoume subject ws available h oti arxise na to kanei supervise?
	// P.3 Add a new diploma thesis subject (title, objectives).
	@Override
	public void addSubject(String full_name, Subject subject) {
		Optional<Subject> checkNameExists= subjectService.findByTitle(subject.getSubjectName());
		if(checkNameExists.isPresent()) {
			throw new RuntimeException("Subject name is already taken");
		}
        Professor professor = retrieveProfile(full_name);
		subject.setProfessor(professor);
		subject.setAssigned(false);
        professor.getSubjects().add(subject);
        professorRepository.save(professor);
	}

	@Override
	public void addThesis(String kati, Thesis thesis) {
		Optional<Thesis> checkNameExists= thesisRepository.findByTitle(thesis.getTitle());
		if(checkNameExists.isPresent()) {
			throw new RuntimeException("Title is already taken");
		}
		Professor professor = retrieveProfile(kati);
		Subject subject= subjectService.findById(thesis.getSubject().getId()).get();

		if(subject.getThesis() != null) {
			throw new RuntimeException("Subject is already assigned to this thesis");
		}
		thesis.setSubject(subject);
		thesis.setProfessor(professor);
		thesisRepository.save(thesis);
	}

	// ennoei available h apo auta pou kanei supervise
	// P.4 Delete diploma thesis subject (title, objectives).
	public void deleteSubject(String full_name, Subject subject) { // logika auta ta orismata thelei? Den thn exei orismenh thn methodo kapou
        Professor professor = retrieveProfile(full_name);
        List<Subject> subjects = professor.getSubjects();
		if(subjects.contains(subject)){
			subject.setProfessor(null);
			subjects.remove(subject);
			professorRepository.save(professor);
		}
	}
	
	// P.5 See the list of applications from the students who want to take over a diploma thesis subject.
	@Override
	public List<Application> listApplications(String full_name, int subject_id) {
        Professor professor = retrieveProfile(full_name);
	    List<Application> applications = new ArrayList<>();
		for(Subject s: professor.getSubjects()) {
			if(s.getId() == subject_id) {
				return s.getApplications();
			}
		}
	    return applications;
	}

	@Override
	public List<Application> listAcceptedApplications(String kati) {
		Professor professor = retrieveProfile(kati);
		List<Application> applications= applicationRepository.findBySubject_IsAssigned(true);
		List<Application> professorApplications= applications.stream().filter(a -> a.getSubject().getProfessor().getId() == professor.getId() && (a.getStatus().equals(ApplicationStatus.ACCEPTED) || a.getStatus().equals(ApplicationStatus.GRADED))).collect(Collectors.toList());
		return professorApplications;
	}

	// P.6 Assign a diploma thesis subject to one of the students who applied for the project.
	// Se random foithth tha to kanw assign? 
	@Override
	public void assignSubject(String strategy, int subject_id) { // maybe subject_id is needed too?
		Subject subject= subjectService.findById(subject_id).get();
	    List<Application> applications= applicationRepository.findBySubject(subject);
		Student assignedStudent = null;
		if(strategy.equals(Strategy.RANDOM.getValue())) {
			assignedStudent= applications.get(getRandomNumber(0,applications.size()-1)).getStudent();
		}
		else {
			BestApplicantStrategyFactory bestStrategy = new BestApplicantStrategyFactory();
			BestApplicantStrategy bestApplicantStrategy = bestStrategy.createStrategy(strategy);
			assignedStudent = bestApplicantStrategy.findBestApplicant(applications);
		}

		Student finalAssignedStudent = assignedStudent;
		Application updateApplicationStatus = applications.stream().filter(a-> a.getStudent().getId() == finalAssignedStudent.getId()).findFirst().get();
		List<Application> updateOtherApplicationStatus = applications.stream().filter(a-> a.getStudent().getId() != finalAssignedStudent.getId()).collect(Collectors.toList());

		for(int i=0;i<updateOtherApplicationStatus.size();i++) {
			updateOtherApplicationStatus.get(i).setStatus(ApplicationStatus.REJECTED);
		}

		subject.setAssigned(true);
		updateApplicationStatus.setStatus(ApplicationStatus.ACCEPTED);
		applicationRepository.save(updateApplicationStatus);
		applicationRepository.saveAll(updateOtherApplicationStatus);
		subjectService.save(subject);
	}

	@Override
	public void deleteThesis(int id) {
		Optional<Thesis> result = thesisRepository.findById((long) id);
		if (result.isPresent() ) {
			thesisRepository.delete(result.get());
		}
		else {
			// we didn't find the professor
			throw new RuntimeException("Did not find thesis id - " + id);
		}
	}

	@Override
	public void deleteSubject(int id) {
			subjectService.delete(id);
	}

	@Override
	public void assignGrade(Application application) {
		Application getApplication = applicationRepository.findById(application.getId()).get();
		getApplication.setReportGrade(application.getReportGrade());
		getApplication.setPresentationGrade(application.getPresentationGrade());
		getApplication.setImplementationGrade(application.getImplementationGrade());
		getApplication.setStatus(ApplicationStatus.GRADED);
		getApplication.setTotalGrade((0.15 * application.getReportGrade()) + (0.15 * application.getPresentationGrade()) + (0.7 * application.getImplementationGrade()));
		applicationRepository.save(getApplication);
	}

	// P.7 Have access to the list of the available diploma thesis subjects that the professor supervises.
	@Override
	public List<Thesis> listProfessorTheses(String full_name) {
        Professor professor = retrieveProfile(full_name);
		return professor.getThesis();
	}

	public int getRandomNumber(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}
}