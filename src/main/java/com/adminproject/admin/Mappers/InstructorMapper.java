package com.adminproject.admin.Mappers;

import com.adminproject.admin.DTOs.InstructorDTO;
import com.adminproject.admin.Entities.Instructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class InstructorMapper {
    public InstructorDTO fromInstructor(Instructor instructor){
        InstructorDTO instructorDTO = new InstructorDTO();
        BeanUtils.copyProperties(instructor,instructorDTO);
        return instructorDTO;
    }
    public Instructor fromInstructorDTO(InstructorDTO instructorDTO){
        Instructor instructor = new Instructor();
        BeanUtils.copyProperties(instructorDTO,instructor);
        return instructor;
    }
}
