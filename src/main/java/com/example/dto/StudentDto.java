package com.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "StudentDto Model Information")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    private Long id;
    @Schema(
            description = "Student First Name"
    )
    @NotEmpty(message = "User first name should not be null or empty")
    private String firstName;
    @Schema(
            description = "Student Last Name"
    )
    @NotEmpty(message = "User last name should not be null or empty")
    private String lastName;
    @Schema(
            description = "Student Email Address"
    )
    @NotEmpty(message = "User email should not be null or empty")
    @Email(message = "Email address should be valid")
    private String email;
}
