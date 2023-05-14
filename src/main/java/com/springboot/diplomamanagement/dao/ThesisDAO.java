package com.springboot.diplomamanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.diplomamanagement.model.Thesis;

import java.util.Optional;

@Repository
public interface ThesisDAO extends JpaRepository<Thesis,Long > { // to long allagh

    Optional<Thesis> findByTitle(String title);
}
