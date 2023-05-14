package com.springboot.diplomamanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.diplomamanagement.model.Student;

import java.util.Optional;

@Repository
public interface StudentDAO extends JpaRepository<Student,String> {
	
	// to jpa dinei kapoies etoimes sunarthseis.
	/* πχ   
	  <S extends T> S save(S entity);      

	  Optional<T> findById(ID primaryKey); 

	  Iterable<T> findAll();               

	  long count();                        

	  void delete(T entity);               

	  boolean existsById(ID primaryKey);   
		 */
	// Epeidh to findByFullName() den einai mesa stis etoimes sunarthseis tou thewrw prepei na graftei edw
	 Optional<Student> findByFullName(String fullName);

	 Optional<Student> findByUser_Username(String username);

}

