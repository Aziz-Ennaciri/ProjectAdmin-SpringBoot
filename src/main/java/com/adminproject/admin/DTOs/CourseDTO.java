package com.adminproject.admin.DTOs;

import lombok.Data;

@Data
public class CourseDTO {
    private Long courseId;
    private String courseName;
    private String courseDuration;
    private String courseDescription;
    private InstructorDTO instructorDTO;
}
