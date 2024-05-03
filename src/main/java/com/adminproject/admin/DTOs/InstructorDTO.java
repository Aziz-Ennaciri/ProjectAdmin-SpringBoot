package com.adminproject.admin.DTOs;

import lombok.Data;

@Data
public class InstructorDTO {
    private Long instructorId;
    private String firstName;
    private String lastName;
    private String summary;
    private UserDTO userDTO;
}
