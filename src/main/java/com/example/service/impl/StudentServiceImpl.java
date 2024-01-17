package com.example.service.impl;

import com.example.dto.StudentDto;
import com.example.entity.Student;
import com.example.exception.EmailAlreadyExistsException;
import com.example.exception.ResourceNotFoundException;
import com.example.mapper.StudentMapper;
import com.example.repository.StudentRepository;
import com.example.service.StudentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public StudentDto createStudent(StudentDto studentDto) {
        log.info("Saving new student");
        Optional<Student> optionalStudent = studentRepository.findByEmail(studentDto.getEmail());
        if(optionalStudent.isPresent()) {
            throw new EmailAlreadyExistsException("Email Already Exists for User");
        }
        Student student = StudentMapper.mapToStudent(studentDto);
        Student savedStudent = studentRepository.save(student);
        StudentDto savedStudentDto = StudentMapper.mapToStudentDto(savedStudent);
        return savedStudentDto;
    }

    @Override
    public StudentDto getStudentById(Long studentId) {
        log.info("Looking for student with id {}", studentId);
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", studentId)
        );
        return StudentMapper.mapToStudentDto(student);
    }

    @Override
    public List<StudentDto> getAllStudents() {
        log.info("Looking for all students, returning list");
        List<Student> students = studentRepository.findAll();
        log.info("Returning list with size: {}", students.size());
        return students.stream().map(StudentMapper::mapToStudentDto)
                .collect(Collectors.toList());
    }

    @Override
    public StudentDto updateStudent(StudentDto student) {
        log.info("Updating student entry");
        Student existingStudent = studentRepository.findById(student.getId()).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", student.getId())
        );
        existingStudent.setFirstName(student.getFirstName());
        existingStudent.setLastName(student.getLastName());
        existingStudent.setEmail(student.getEmail());
        Student updatedStudent = studentRepository.save(existingStudent);
        return StudentMapper.mapToStudentDto(updatedStudent);
    }

    @Override
    public void deleteStudent(Long id) {
        log.info("Deleting student with id {}", id);
        Student existingStudent = studentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );

        studentRepository.deleteById(id);
    }

}
