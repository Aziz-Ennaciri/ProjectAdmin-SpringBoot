package com.adminproject.admin.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id",nullable = false)
    private Long courseId;

    @Basic
    @Column(name = "course_name",nullable = false,length = 45)
    private String courseName;

    @Basic
    @Column(name = "course_duration",nullable = false,length = 45)
    private String courseDuration;

    @Basic
    @Column(name = "course_description",nullable = false,length = 65)
    private String courseDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id",referencedColumnName = "instructor_id",nullable = false)
    private Instructor instructor;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "enrolled_in",joinColumns = {@JoinColumn(name = "course_id")},
            inverseJoinColumns = {@JoinColumn(name = "student_id")})
    private Set<Student> students = new HashSet<>();

    public Course(String courseName, String courseDuration, String courseDescription, Instructor instructor) {
        this.courseName=courseName;
        this.courseDuration=courseDuration;
        this.courseDescription=courseDescription;
        this.instructor=instructor;
    }

    public void assignStudentToCourse(Student student) {
        this.students.add(student);
        student.getCourses().add(this);
    }
    public void removeStudentFromCourse(Student student){
        this.students.remove(student);
        student.getCourses().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;
        return Objects.equals(courseId, course.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId);
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", instructor=" + (instructor != null ? instructor.getInstructorId() : null) +
                '}';
    }



}
