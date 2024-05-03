package com.adminproject.admin.Services.impl;

import com.adminproject.admin.DAO.InstructorRepository;
import com.adminproject.admin.DTOs.InstructorDTO;
import com.adminproject.admin.Entities.Course;
import com.adminproject.admin.Entities.Instructor;
import com.adminproject.admin.Entities.User;
import com.adminproject.admin.Mappers.InstructorMapper;
import com.adminproject.admin.Services.IInstructorService;
import com.adminproject.admin.Services.IUserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class InstructorServiceImpl implements IInstructorService {
    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private InstructorMapper instructorMapper;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private CourseServiceImpl courseService;
    @Override
    public Instructor loadInstructorById(Long instructorId) {
        return instructorRepository.findById(instructorId).orElseThrow(()->new EntityNotFoundException("Instructor with this ID"+instructorId+"not found"));
    }

    @Override
    public Page<InstructorDTO> findInstructorsByName(String name, int page, int size) {
        PageRequest pageRequest=PageRequest.of(page,size);
        Page<Instructor> instructorsPage=instructorRepository.findInstructorsByName(name,pageRequest);
        return new PageImpl<>(instructorsPage.getContent().stream().map(instructor -> instructorMapper.fromInstructor(instructor)).collect(Collectors.toList()),pageRequest,instructorsPage.getTotalElements());
    }

    @Override
    public InstructorDTO loadInstructorByEmail(String email) {
        return instructorMapper.fromInstructor(instructorRepository.findInstructorByEmail(email));
    }

    @Override
    public InstructorDTO createInstructor(InstructorDTO instructorDTO) {
        User user=iUserService.createUser(instructorDTO.getUserDTO().getEmail(),instructorDTO.getUserDTO().getPassword());
        iUserService.assignRoleToUser(user.getEmail(),"instructor");
        Instructor instructor=instructorMapper.fromInstructorDTO(instructorDTO);
        instructor.setUser(user);
        Instructor savedInstructor = instructorRepository.save(instructor);
        return instructorMapper.fromInstructor(savedInstructor);
    }

    @Override
    public InstructorDTO updateInstructor(InstructorDTO instructorDTO) {
        Instructor loadInstructor = loadInstructorById(instructorDTO.getInstructorId());
        Instructor instructor =instructorMapper.fromInstructorDTO(instructorDTO);
        instructor.setUser(loadInstructor.getUser());
        instructor.setCourses(loadInstructor.getCourses());
        Instructor savedInstructor = instructorRepository.save(instructor);
        return instructorMapper.fromInstructor(savedInstructor);
    }

    @Override
    public List<InstructorDTO> fetchInstructors() {
        return instructorRepository.findAll().stream().map(instructor -> instructorMapper.fromInstructor(instructor)).collect(Collectors.toList());
    }

    @Override
    public void removeInstructor(Long instructorId) {
        Instructor instructor = loadInstructorById(instructorId);
        for (Course course : instructor.getCourses()){
            courseService.removeCourse(course.getCourseId());
        }
        instructorRepository.deleteById(instructorId);
    }
}
