package com.springboot.diplomamanagement.service;

import java.util.*;

import com.springboot.diplomamanagement.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.diplomamanagement.dao.UserDAO;
import com.springboot.diplomamanagement.model.User;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {


	private UserDAO userDAO;

	public UserServiceImpl(UserDAO userDAO){
		this.userDAO= userDAO;
	}
	
	@Override
	public void saveUser(User user) {
		if(isUserPresent(user)) {
			throw new RuntimeException("Username is already in use");
		}
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		userDAO.save(user);
	}

	@Override
	public boolean isUserPresent(User user) {
		Optional<User> storedUser = userDAO.findByUsername(user.getUsername());
		return storedUser.isPresent();
	}

	@Override
	public Optional<User> findById(int id) {
		Optional<User> result = userDAO.findById(id);

		if (result.isPresent()) {
			return result;
		}
		else {
			// we didn't find the user
			throw new RuntimeException("Did not find user id - " + id);
		}
	}

	@Override
	public Optional<User> findByUserName(String userName) {
		Optional<User> result = userDAO.findByUsername(userName);

		if (result.isPresent() ) {
			return result;
		}
		else {
			// we didn't find the user
			throw new RuntimeException("Did not find user name - " + userName);
		}
	}

	@Override
	public UserDetails loadUserByUsername(String userName) {
		User user = userDAO.findByUsername(userName).orElseThrow(()-> new UsernameNotFoundException(
						String.format("USER_NOT_FOUND", userName)));

		List<GrantedAuthority> authorities = getUserAuthority(Set.of(user.getRole()));
		return buildUserForAuthentication(user, authorities);
	}

	private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
		Set<GrantedAuthority> roles = new HashSet<>();
		for (Role role : userRoles) {
			roles.add(new SimpleGrantedAuthority(role.getValue()));
		}
		return new ArrayList<>(roles);
	}

	private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				true, true, true, true, authorities);
	}
}
