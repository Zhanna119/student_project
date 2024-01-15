package com.example.controller;

import com.example.dto.StudentDto;
import com.example.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
     name = "CRUD REST APIs for Student Resource",
     description = "CRUD REST APIs - Create Student, Update Student, Get Student, Get All Students, Delete Student"
)
@RestController
@AllArgsConstructor
@RequestMapping("api/students")
public class StudentController {
    private StudentService studentService;

    @Operation(
            summary = "Create Student REST API",
            description = "Create Student REST API is used to save student in database"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Operation completed successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input"),
            @ApiResponse(responseCode = "404", description = "Not Found - Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Something went wrong on the server")
    })
    @PostMapping
    public ResponseEntity<StudentDto> createStudent(@Valid @RequestBody StudentDto student) {
        StudentDto savedStudent = studentService.createStudent(student);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get Student by ID REST API",
            description = "Get Student By ID REST API is used to get a single student from the database"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Operation completed successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input"),
            @ApiResponse(responseCode = "404", description = "Not Found - Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Something went wrong on the server")
    })
    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable("id") Long studentId) {
        StudentDto student = studentService.getStudentById(studentId);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @Operation(
            summary = "Get All Students REST API",
            description = "Get All Students REST API is used to get all the students from the database"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Operation completed successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input"),
            @ApiResponse(responseCode = "404", description = "Not Found - Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Something went wrong on the server")
    })
    @GetMapping("/all")
    public ResponseEntity<List<StudentDto>> getAllStudent() {
        List<StudentDto> students = studentService.getAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @Operation(
            summary = "Update Student REST API",
            description = "Update Student REST API is used to a particular user in the database"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Operation completed successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input"),
            @ApiResponse(responseCode = "404", description = "Not Found - Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Something went wrong on the server")
    })
    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable("id") Long studentId,
                                           @RequestBody @Valid StudentDto student) {
        student.setId(studentId);
        StudentDto updatedStudent = studentService.updateStudent(student);
        return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Student REST API",
            description = "Delete Student REST API is used to delete a particular student from the database"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Operation completed successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input"),
            @ApiResponse(responseCode = "404", description = "Not Found - Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Something went wrong on the server")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable("id") Long studentId) {
        studentService.deleteStudent(studentId);
        return new ResponseEntity<>("Student successfully deleted!", HttpStatus.OK);
    }
}
