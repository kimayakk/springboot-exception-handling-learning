package com.Learn.ExceptionHandling.Dto;

import jakarta.persistence.Column;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class EmployeeDto {

    private  Long id;

    @NotBlank(message ="Name should not be blank")
    private String name;

    @Max(value=75)
    @Min(value=20)
    private Integer age;

    @Max(value=1000000)
    @Min(value=10000)
    private BigDecimal salary;

    @Column(unique = true)
    private String email;

    @Pattern(regexp = "^EMPLOYEE|ADMIN|CUSTOMER$", message = "Role should be either Employee,Admin or Customer only.")
    private String role;

    @PastOrPresent
    private LocalDate dateOfJoining;
}
