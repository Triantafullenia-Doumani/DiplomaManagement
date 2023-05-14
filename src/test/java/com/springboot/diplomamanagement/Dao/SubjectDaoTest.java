package com.springboot.diplomamanagement.Dao;

import com.springboot.diplomamanagement.dao.SubjectDAO;
import com.springboot.diplomamanagement.model.Subject;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SubjectDaoTest {

    @Mock
    SubjectDAO subjectDAO;

    @Test
    public void getAllSubjectsTest(){
        subjectDAO= mock(SubjectDAO.class);
        when(subjectDAO.findAll()).thenReturn(new ArrayList<>());
        List<Subject> subjectList= subjectDAO.findAll();
        assertEquals(0,subjectList.size());
    }
}
