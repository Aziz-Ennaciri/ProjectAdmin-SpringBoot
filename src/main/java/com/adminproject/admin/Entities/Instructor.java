package com.adminproject.admin.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

@Entity
@Table(name = "instructors")
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "instructor_id",nullable = false)
    private Long instructorId;

    @Basic
    @Column(name = "first_name",nullable = false,length = 45)
    private String firstName;

    @Basic
    @Column(name = "last_name",nullable = false,length = 45)
    private String lastName;

    @Basic
    @Column(name = "summary",nullable = false,length = 64)
    private String summary;

    @OneToMany(mappedBy = "instructor",fetch = FetchType.LAZY)
    private Set<Course> courses = new HashSet<>();

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id",referencedColumnName = "user_id",nullable = false)
    private User user;

    public Instructor(String firstName, String lastName,String summary,User user) {
        this.firstName=firstName;
        this.lastName=lastName;
        this.summary=summary;
        this.user=user;
    }
    @Override
    public String toString() {
        return "Instructor{" +
                "instructorId=" + instructorId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", summary='" + summary + '\'' +
                ", coursesCount=" + courses.size() + // Print the count of associated courses
                ", user=" + (user != null ? user.getUserId() : null) +
                '}';
    }


}
