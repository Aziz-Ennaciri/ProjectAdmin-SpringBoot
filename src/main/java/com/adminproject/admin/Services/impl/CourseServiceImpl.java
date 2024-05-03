package com.adminproject.admin.Services.impl;

import com.adminproject.admin.DAO.CourseRepository;
import com.adminproject.admin.DAO.InstructorRepository;
import com.adminproject.admin.DAO.StudentRepository;
import com.adminproject.admin.DTOs.CourseDTO;
import com.adminproject.admin.Entities.Course;
import com.adminproject.admin.Entities.Instructor;
import com.adminproject.admin.Entities.Student;
import com.adminproject.admin.Mappers.CourseMapper;
import com.adminproject.admin.Services.ICourseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Transactional
@Service
public class CourseServiceImpl implements ICourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Override
    public Course loudCourseById(Long courseId) {
        return courseRepository.findById(courseId).orElseThrow(()->new EntityNotFoundException("Course with this ID"+courseId+"Not Found"));
    }

    @Override
    public CourseDTO createCourse(CourseDTO courseDTO) {
        if (courseDTO.getInstructorDTO() == null) {
            throw new IllegalArgumentException("InstructorDTO is null");
        }
        Long instructorId = courseDTO.getInstructorDTO().getInstructorId();
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new EntityNotFoundException("Instructor with ID " + instructorId + " not found"));
        Course course = courseMapper.fromCourseDTO(courseDTO);
        course.setInstructor(instructor);
        Course savedCourse = courseRepository.save(course);
        return courseMapper.fromCourse(savedCourse);
    }


    @Override
    public CourseDTO updateCourse(CourseDTO courseDTO) {
        Course loadCourse = loudCourseById(courseDTO.getCourseId());
        Instructor instructor = instructorRepository.findById(courseDTO.getInstructorDTO().getInstructorId()).orElseThrow(()->new EntityNotFoundException("Instructor with this ID"+courseDTO.getInstructorDTO().getInstructorId()+"Not Found"));
        Course course=courseMapper.fromCourseDTO(courseDTO);
        course.setInstructor(instructor);
        course.setStudents(loadCourse.getStudents());
        Course updatedCourse = courseRepository.save(course);
        return courseMapper.fromCourse(updatedCourse);
    }

    @Override
    public Page<CourseDTO> findCoursesByCourseName(String keyword, int page, int size) {
        PageRequest pageRequest= PageRequest.of(page,size);
        Page<Course> pageCourses = courseRepository.findCoursesByCourseNameContains(keyword,pageRequest);
        return new PageImpl<>(pageCourses.getContent().stream().map(course -> courseMapper.fromCourse(course)).collect(Collectors.toList()),pageRequest,pageCourses.getTotalElements());
    }

    @Override
    public void assignStudentToCourse(Long courseId, Long StudentId) {
        Student student=studentRepository.findById(StudentId).orElseThrow(()->new EntityNotFoundException("Student with this ID"+StudentId+"Not Found"));
        Course course=loudCourseById(courseId);
        course.assignStudentToCourse(student);
    }

    @Override
    public Page<CourseDTO> fetchCoursesForStudent(Long studentId, int page, int size) {
        PageRequest pageRequest=PageRequest.of(page,size);
        Page<Course> studentCoursesPage = courseRepository.getCoursesByStudentId(studentId,pageRequest);
        return new PageImpl<>(studentCoursesPage.getContent().stream().map(course -> courseMapper.fromCourse(course)).collect(Collectors.toList()),pageRequest,studentCoursesPage.getTotalElements());
    }

    @Override
    public Page<CourseDTO> fetchNonEnrolledInCoursesForStudent(Long studentId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<Course> nonEnrolledInCoursesPage = courseRepository.getNotEnrolledInCoursesByStudentId(studentId,pageRequest);
        return new PageImpl<>(nonEnrolledInCoursesPage.getContent().stream().map(course -> courseMapper.fromCourse(course)).collect(Collectors.toList()),pageRequest,nonEnrolledInCoursesPage.getTotalElements());
    }

    @Override
    public void removeCourse(Long courseId) {
        courseRepository.deleteById(courseId);
    }

    @Override
    public Page<CourseDTO> fetchCoursesForInstructor(Long instructorId, int page, int size) {
        PageRequest pageRequest= PageRequest.of(page,size);
        Page<Course> instructorCoursesPage=courseRepository.getCoursesByInstructorId(instructorId,pageRequest);
        return new PageImpl<>(instructorCoursesPage.getContent().stream().map(course -> courseMapper.fromCourse(course)).collect(Collectors.toList()),pageRequest,instructorCoursesPage.getTotalElements());
    }
}
