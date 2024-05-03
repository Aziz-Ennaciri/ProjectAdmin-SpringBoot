package com.adminproject.admin.Services;

import com.adminproject.admin.DTOs.CourseDTO;
import com.adminproject.admin.Entities.Course;
import org.springframework.data.domain.Page;

public interface ICourseService {
    Course loudCourseById(Long courseId);
    CourseDTO createCourse(CourseDTO courseDTO);
    CourseDTO updateCourse(CourseDTO courseDTO);
    Page<CourseDTO> findCoursesByCourseName(String keyword,int page,int size);
    void assignStudentToCourse(Long courseId,Long StudentId);
    Page<CourseDTO> fetchCoursesForStudent(Long studentId,int page , int size);
    Page<CourseDTO> fetchNonEnrolledInCoursesForStudent(Long studentId,int page, int size);
    void removeCourse(Long courseId);
    Page<CourseDTO> fetchCoursesForInstructor(Long instructorId,int page,int size);
}
