package com.adminproject.admin.DTOs;

import lombok.Data;

@Data
public class StudentDTO {
    private Long studentId;
    private String firstName;
    private String lastName;
    private String level;
    private UserDTO userDto;
}
