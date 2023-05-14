package com.springboot.diplomamanagement.Service;

import com.springboot.diplomamanagement.dao.ApplicationDAO;
import com.springboot.diplomamanagement.dao.ProfessorDAO;
import com.springboot.diplomamanagement.dao.StudentDAO;
import com.springboot.diplomamanagement.dao.ThesisDAO;
import com.springboot.diplomamanagement.model.Professor;
import com.springboot.diplomamanagement.model.User;
import com.springboot.diplomamanagement.service.ProfessorServiceImpl;
import com.springboot.diplomamanagement.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProfessorServiceTest {

    @Mock
    ProfessorDAO professorRepository;

    @Mock
    StudentDAO studentDAO;
    @Mock
    ThesisDAO thesisDAO;
    @Mock
    ApplicationDAO applicationDAO;

    ProfessorServiceImpl professorService;

    @Mock
    UserServiceImpl userService;

    @Test
    public void retrieveProfileTest() {
        professorService = new ProfessorServiceImpl(professorRepository,applicationDAO,thesisDAO,studentDAO,userService);
        Professor professor= new Professor();
        professor.setId(1);
        when(professorRepository.findByUser_Username(any())).thenReturn(Optional.of(professor));
        Professor response= professorService.retrieveProfile("test");
        assertEquals(response.getId(),1);
    }

    @Test
    public void saveProfileTest() {
        professorService = new ProfessorServiceImpl(professorRepository,applicationDAO,thesisDAO,studentDAO,userService);
        Professor professor= new Professor();
        professor.setId(1);
        User user= new User();
        user.setUsername("test");
        professor.setUser(user);
        when(professorRepository.save(any())).thenReturn(professor);
        when(userService.findByUserName(any())).thenReturn(Optional.of(new User()));
        professorService.saveProfile(professor);
    }
}
