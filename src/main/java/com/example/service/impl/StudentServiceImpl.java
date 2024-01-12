package com.example.service.impl;

import com.example.entity.Student;
import com.example.repository.StudentRepository;
import com.example.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    @Override
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student getStudentById(Long studentId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        return optionalStudent.get();
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student updateStudent(Student student) {
        Student existingStudent = studentRepository.findById(student.getId()).get();
        existingStudent.setFirstName(student.getFirstName());
        existingStudent.setLastName(student.getLastName());
        existingStudent.setEmail(student.getEmail());
        Student updatedStudent = studentRepository.save(existingStudent);
        return updatedStudent;
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
