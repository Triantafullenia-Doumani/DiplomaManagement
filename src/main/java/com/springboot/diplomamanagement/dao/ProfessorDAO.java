package com.springboot.diplomamanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.diplomamanagement.model.Professor;

import java.util.Optional;

@Repository
public interface ProfessorDAO extends JpaRepository<Professor,String > { //String?? If string is the type of the entity's primary key, then yes
	
	// to JpaRepository dinei kapoies etoimes sunarthseis.
	/* πχ   
  <S extends T> S save(S entity);      

  Optional<T> findById(ID primaryKey); 

  Iterable<T> findAll();               

  long count();                        

  void delete(T entity);               

  boolean existsById(ID primaryKey);   
	 */
	// Epeidh to findByFullName() den einai mesa stis etoimes sunarthseis tou thewrw prepei na graftei edw
	Optional<Professor> findByFullName(String fullName);
	Optional<Professor> findByUser_Username(String fullName);
}
