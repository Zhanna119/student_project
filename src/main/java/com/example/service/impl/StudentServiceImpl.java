package com.example.service.impl;

import com.example.dto.StudentDto;
import com.example.entity.Student;
import com.example.exception.EmailAlreadyExistsException;
import com.example.exception.ResourceNotFoundException;
import com.example.mapper.StudentMapper;
import com.example.repository.StudentRepository;
import com.example.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    @Override
    public StudentDto createStudent(StudentDto studentDto) {
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
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", studentId)
        );
        return StudentMapper.mapToStudentDto(student);
    }

    @Override
    public List<StudentDto> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(StudentMapper::mapToStudentDto)
                .collect(Collectors.toList());
    }

    @Override
    public StudentDto updateStudent(StudentDto student) {
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
        Student existingStudent = studentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );

        studentRepository.deleteById(id);
    }
}
