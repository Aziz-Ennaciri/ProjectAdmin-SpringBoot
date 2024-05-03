package com.adminproject.admin.Services;

import com.adminproject.admin.DTOs.InstructorDTO;
import com.adminproject.admin.Entities.Instructor;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IInstructorService {
    Instructor loadInstructorById(Long instructorId);
    Page<InstructorDTO> findInstructorsByName(String name,int page, int size);
    InstructorDTO loadInstructorByEmail(String email);
    InstructorDTO createInstructor(InstructorDTO instructorDTO);
    InstructorDTO updateInstructor(InstructorDTO instructorDTO);
    List<InstructorDTO> fetchInstructors();
    void removeInstructor(Long instructorId);
}
