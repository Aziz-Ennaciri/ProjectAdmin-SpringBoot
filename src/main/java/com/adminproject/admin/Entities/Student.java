package com.adminproject.admin.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id",nullable = false)
    private Long studentId;

    @Basic
    @Column(name = "first_name",nullable = false,length =45)
    private String firstName;

    @Basic
    @Column(name = "last_name",nullable = false,length = 45)
    private String lastName;

    @Basic
    @Column(name = "level",nullable = false,length = 45)
    private String level;

    @ManyToMany(mappedBy = "students",fetch = FetchType.LAZY)
    private Set<Course> courses = new HashSet<>();

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id",referencedColumnName = "user_id",nullable = false)
    private User user;


    public Student(String firstName, String lastName, String level, User user) {
        this.firstName=firstName ;
        this.lastName=lastName;
        this.level=level;
        this.user=user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;
        return Objects.equals(studentId, student.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId);
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", level='" + level + '\'' +
                ", coursesCount=" + courses.size() + // Print the count of associated courses
                ", user=" + (user != null ? user.getUserId() : null) +
                '}';
    }


}
