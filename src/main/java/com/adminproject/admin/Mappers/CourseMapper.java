package com.adminproject.admin.Mappers;

import com.adminproject.admin.DTOs.CourseDTO;
import com.adminproject.admin.Entities.Course;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseMapper {
    @Autowired
    private  InstructorMapper instructorMapper;

    public CourseMapper(InstructorMapper instructorMapper) {
        this.instructorMapper = instructorMapper;
    }

    public CourseDTO fromCourse(Course course){
        CourseDTO courseDTO = new CourseDTO();
        BeanUtils.copyProperties(course,courseDTO);
        courseDTO.setInstructorDTO(instructorMapper.fromInstructor(course.getInstructor()));
        return courseDTO;
    }

    public Course fromCourseDTO(CourseDTO courseDTO){
        Course course = new Course();
        BeanUtils.copyProperties(courseDTO,course);
        return course;
    }
}
