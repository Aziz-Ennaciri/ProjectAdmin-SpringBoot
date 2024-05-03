package com.adminproject.admin.DAO;

import com.adminproject.admin.Entities.Instructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InstructorRepository extends JpaRepository<Instructor,Long> {
    @Query(value = "select i from Instructor as i where i.firstName like %:name% or i.lastName like %:name%")
    Page<Instructor> findInstructorsByName(@Param("name") String name, Pageable pageable);

    @Query(value = "select i from Instructor as i where i.user.email=:email ")
    Instructor findInstructorByEmail(@Param("email") String email);
}
