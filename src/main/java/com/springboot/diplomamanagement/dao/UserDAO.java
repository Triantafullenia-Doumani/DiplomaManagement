package com.springboot.diplomamanagement.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.diplomamanagement.model.User;

/*The JpaRepository interface is a generic interface that takes two type parameters:
 the entity type that the repository manages (in this case, User) and the type of the ID property
 of the entity (in this case, Integer). This allows Spring Data JPA to provide implementations 
 for the standard repository methods based on the type of the entity and its ID.
 JpaRepository interface, is provided by Spring Data JPA and provides a set of standard
 methods for performing CRUD (create, read, update, delete) operations on entities.
 */
@Repository
public interface UserDAO extends JpaRepository<User,Integer>{

	Optional<User> findByUsername(String username);
}

