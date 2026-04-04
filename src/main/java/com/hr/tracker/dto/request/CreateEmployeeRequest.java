package com.hr.tracker.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmployeeRequest {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Department is required")
    private String department;
    @NotBlank(message = "Role is required")
    private String role;
    @NotNull(message = "Joining date is required")
    private LocalDate joiningDate;
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;
}
