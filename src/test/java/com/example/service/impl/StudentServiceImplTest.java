package com.example.service.impl;

import com.example.dto.StudentDto;
import com.example.entity.Student;
import com.example.mapper.StudentMapper;
import com.example.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {
    @Mock
    private StudentRepository repository;

    @Mock
    private StudentMapper mapper;

    @InjectMocks
    private StudentServiceImpl service;

    @Test
    void testGetAllStudents_Successful() {
        List<Student> mockStudents = Arrays.asList(
                new Student(1L, "Name1", "Surname1", "email1@example.com"),
                new Student(2L, "Name2", "Surname2", "email2@example.com"),
                new Student(3L, "Name3", "Surname3", "email3@example.com")
        );
        when(repository.findAll()).thenReturn(mockStudents);
        List<StudentDto> list = service.getAllStudents();
        assertEquals(3, list.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetAllStudents_WhenEmpty() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        List<StudentDto> result = service.getAllStudents();
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findAll();
    }
}