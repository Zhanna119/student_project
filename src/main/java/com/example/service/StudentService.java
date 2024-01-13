package com.example.service;


import com.example.dto.StudentDto;

import java.util.List;

public interface StudentService {
    StudentDto createStudent(StudentDto student);
    StudentDto getStudentById(Long id);
    List<StudentDto> getAllStudents();
    StudentDto updateStudent(StudentDto student);
    void deleteStudent(Long id);

}
