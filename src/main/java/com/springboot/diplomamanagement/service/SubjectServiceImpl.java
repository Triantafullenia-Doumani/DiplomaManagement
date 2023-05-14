package com.springboot.diplomamanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.diplomamanagement.dao.SubjectDAO;
import com.springboot.diplomamanagement.model.Subject;

@Service
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private SubjectDAO subjectRepository;
	
	@Override
	public void save(Subject subject) {
		subjectRepository.save(subject);
	}

	@Override
	public void delete(int id) {
		Optional<Subject> result = subjectRepository.findById(id);

		if (result.isPresent()) {
			subjectRepository.delete(result.get());
		}
		else {
			// we didn't find subject
			throw new RuntimeException("Did not find subject - " + id);
		}
	}

	@Override
	public List<Subject> findAll() {
		return subjectRepository.findAll();
	}

	@Override
	public Optional<Subject> findById(int id) {
		Optional<Subject> result = subjectRepository.findById(id);
		
		if (result != null ) {
			return result;
		}
		else {
			// we didn't find subject
			throw new RuntimeException("Did not find subject - " + id);
		}
	}

	@Override
	public Optional<Subject> findByTitle(String title) {
		Optional<Subject> result = subjectRepository.findBySubjectName(title);
		return result;
	}


}