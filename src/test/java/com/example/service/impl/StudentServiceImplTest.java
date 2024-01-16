package com.example.service.impl;

import com.example.dto.StudentDto;
import com.example.entity.Student;
import com.example.exception.EmailAlreadyExistsException;
import com.example.exception.ResourceNotFoundException;
import com.example.mapper.StudentMapper;
import com.example.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    /* @Test
    void testCreateStudent_Successful() {
        StudentDto inputDto = new StudentDto(1L, "Name1", "Surname1", "email1@example.com");
        Student inputStudent = StudentMapper.mapToStudent(inputDto);
        Student savedStudent = new Student(1L, "Name1", "Surname1", "email1@example.com");
        StudentDto expectedDto = new StudentDto(1L, "Name1", "Surname1", "email1@example.com");
        when(repository.findByEmail(inputDto.getEmail())).thenReturn(Optional.empty());
        when(repository.save(inputStudent)).thenReturn(savedStudent);
        when(mapper.mapToStudentDto(savedStudent)).thenReturn(expectedDto);
        StudentDto resultDto = service.createStudent(inputDto);
        assertEquals(expectedDto, resultDto);
        verify(repository, times(1)).findByEmail(inputDto.getEmail());
        verify(repository, times(1)).save(inputStudent);
        verify(mapper, times(1)).mapToStudentDto(savedStudent);
    }*/

    @Test
    void testCreateStudent_EmailAlreadyExists() {
        StudentDto inputDto = new StudentDto();
        Student existingStudent = new Student();
        when(repository.findByEmail(inputDto.getEmail())).thenReturn(Optional.of(existingStudent));
        assertThrows(EmailAlreadyExistsException.class, () -> service.createStudent(inputDto));
        verify(repository, times(1)).findByEmail(inputDto.getEmail());
        verifyNoMoreInteractions(repository, mapper);
    }

    /*@Test
    void testGetStudentById_Successful() {
        Long studentId = 1L;
        Student student = new  Student(1L, "Name1", "Surname1", "email1@example.com");
        StudentDto expectedDto = new StudentDto(1L, "Name1", "Surname1", "email1@example.com");
        when(repository.findById(studentId)).thenReturn(Optional.of(student));
        when(mapper.mapToStudentDto(student)).thenReturn(expectedDto);
        StudentDto resultDto = service.getStudentById(studentId);
        assertEquals(expectedDto, resultDto);
        verify(repository, times(1)).findById(studentId);
        verify(mapper, times(1)).mapToStudentDto(student);
    }*/

    @Test
    void testGetStudentById_NotFound() {
        Long studentId = 99L;
        when(repository.findById(studentId)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getStudentById(studentId));
        verify(repository, times(1)).findById(studentId);
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    void testGetAllStudents_Successful() {
        List<Student> mockStudents = Arrays.asList(
                new Student(1L, "Name1", "Surname1", "email1@example.com"),
                new Student(2L, "Name2", "Surname2", "email2@example.com"),
                new Student(3L, "Name3", "Surname3", "email3@example.com"));
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

    /*@Test
    void testUpdateStudent_Successful() {
        StudentDto inputDto = new StudentDto(1L, "Name1", "Surname1", "email1@example.com");
        Student existingStudent = new Student(1L, "Name1", "Surname1", "email1@example.com");
        Student updatedStudent = new Student(1L, "Name1", "Surname1", "email1@example.com");
        StudentDto expectedDto = new StudentDto(1L, "Name1", "Surname1", "email1@example.com");
        when(repository.findById(inputDto.getId())).thenReturn(Optional.of(existingStudent));
        when(repository.save(existingStudent)).thenReturn(updatedStudent);
        when(mapper.mapToStudentDto(updatedStudent)).thenReturn(expectedDto);
        StudentDto resultDto = service.updateStudent(inputDto);
        assertEquals(expectedDto, resultDto);
        verify(repository, times(1)).findById(inputDto.getId());
        verify(repository, times(1)).save(existingStudent);
        verify(mapper, times(1)).mapToStudentDto(updatedStudent);
    }*/

    @Test
    void testUpdateStudent_NotFound() {
        StudentDto inputDto = new StudentDto();
        when(repository.findById(inputDto.getId())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.updateStudent(inputDto));
        verify(repository, times(1)).findById(inputDto.getId());
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    void testDeleteStudent_Successful() {
        Long studentId = 1L;
        Student existingStudent = new Student(1L, "Name1", "Surname1", "email1@example.com");
        when(repository.findById(studentId)).thenReturn(Optional.of(existingStudent));
        service.deleteStudent(studentId);
        verify(repository, times(1)).findById(studentId);
        verify(repository, times(1)).deleteById(studentId);
    }

    @Test
    void testDeleteStudent_NotFound() {
        Long studentId = 99L;
        when(repository.findById(studentId)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.deleteStudent(studentId));
        verify(repository, times(1)).findById(studentId);
        verifyNoMoreInteractions(repository);
    }
}
