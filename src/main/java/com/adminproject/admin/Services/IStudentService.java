package com.adminproject.admin.Services;

import com.adminproject.admin.DTOs.StudentDTO;
import com.adminproject.admin.Entities.Student;
import org.springframework.data.domain.Page;

public interface IStudentService {
    Student loadStudent(Long studentId);
    Page<StudentDTO> loadStudentsByName(String name,int page,int size);
    StudentDTO loadStudentByEmail(String email);
    StudentDTO createStudent(StudentDTO studentDTO);
    StudentDTO updateStudent(StudentDTO studentDTO);
    void removeStudent(Long studentId);
}
