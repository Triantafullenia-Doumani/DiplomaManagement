package com.springboot.diplomamanagement.service;

import java.util.Optional;

import com.springboot.diplomamanagement.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
	
	public void saveUser(User user);
	public boolean isUserPresent(User user);
	public Optional<User> findById(int id);

	public Optional<User> findByUserName(String userName);
}