package com.springboot.diplomamanagement.Service;

import com.springboot.diplomamanagement.dao.*;
import com.springboot.diplomamanagement.model.Professor;
import com.springboot.diplomamanagement.model.Role;
import com.springboot.diplomamanagement.model.User;
import com.springboot.diplomamanagement.service.ProfessorServiceImpl;
import com.springboot.diplomamanagement.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    ProfessorDAO professorRepository;

    @Mock
    StudentDAO studentDAO;

    @Mock
    UserDAO userDAO;
    @Mock
    ThesisDAO thesisDAO;
    @Mock
    ApplicationDAO applicationDAO;

    @Mock
    ProfessorServiceImpl professorService;
    UserServiceImpl userService;

    @Test
    public void loadUserByUserNameTest() {
        userService = new UserServiceImpl(userDAO);
        User user= new User();
        user.setUsername("test");
        user.setPassword("test");
        user.setRole(Role.USER);
        when(userDAO.findByUsername(any())).thenReturn(Optional.of(user));
        UserDetails response= userService.loadUserByUsername("test");
        assertNotNull(response);
    }

    @Test
    public void saveUserTest() {
        userService = new UserServiceImpl(userDAO);
        User user= new User();
        user.setUsername("test");
        user.setPassword("test");
        user.setRole(Role.USER);
        when(userDAO.save(any())).thenReturn(user);
        userService.saveUser(user);
    }
}
