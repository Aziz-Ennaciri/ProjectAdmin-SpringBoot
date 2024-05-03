package com.adminproject.admin.Runner;

import com.adminproject.admin.DTOs.CourseDTO;
import com.adminproject.admin.DTOs.InstructorDTO;
import com.adminproject.admin.DTOs.StudentDTO;
import com.adminproject.admin.DTOs.UserDTO;
import com.adminproject.admin.Entities.Student;
import com.adminproject.admin.Entities.User;
import com.adminproject.admin.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class MyRunner implements CommandLineRunner {
    @Autowired
    private IRoleService iRoleService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IInstructorService iInstructorService;
    @Autowired
    private ICourseService courseService;
    @Autowired
    private IStudentService studentService;


    @Override
    public void run(String... args) throws Exception {
        createRoles();
        createAdmin();
        createInstructor();
        createCourses();
        StudentDTO studentDTO =createStudent();
        assignCourseToStudent(studentDTO);
        createStudents();
    }

    private void createStudents() {
        for (int i=0;i<10;i++){
            StudentDTO studentDTO = new StudentDTO();
            studentDTO.setFirstName("student FN"+i);
            studentDTO.setLastName(" student LN"+i);
            studentDTO.setLevel("intermediate"+i);
            UserDTO userDTO = new UserDTO();
            userDTO.setEmail("student"+i+   "@gmail.com");
            userDTO.setPassword("1234");
            studentDTO.setUserDto(userDTO);
            studentService.createStudent(studentDTO);
        }
    }


    private void createRoles() {
        Arrays.asList("Admin","Instructor","Student").forEach(role->iRoleService.createRole(role));
    }
    private void createAdmin() {
        iUserService.createUser("admin@gmail.com","1234");
        iUserService.assignRoleToUser("admin@gmail.com","Admin");
    }

    private void createInstructor() {
        for (int i=0;i<10;i++){
            InstructorDTO instructorDTO = new InstructorDTO();
            instructorDTO.setFirstName("instructor"+i+"FN");
            instructorDTO.setLastName("instructor"+i+"LN");
            instructorDTO.setSummary("master"+i);
            UserDTO userDTO = new UserDTO();
            userDTO.setEmail("instructor"+i+"@gmail.com");
            userDTO.setPassword("1234");
            instructorDTO.setUserDTO(userDTO);
            iInstructorService.createInstructor(instructorDTO);
        }
    }

    private void createCourses() {
        for (int i=0;i<20;i++){
            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setCourseName("Java course"+i);
            courseDTO.setCourseDescription("description"+i);
            courseDTO.setCourseDuration("duration"+i);
            InstructorDTO instructorDTO = new InstructorDTO();
            instructorDTO.setInstructorId(1L);
            courseDTO.setInstructorDTO(instructorDTO);
            courseService.createCourse(courseDTO);
        }
    }

    private StudentDTO createStudent() {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setFirstName("student FN");
        studentDTO.setLastName(" student LN");
        studentDTO.setLevel("intermediate");
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("student@gmail.com");
        userDTO.setPassword("1234");
        studentDTO.setUserDto(userDTO);
        return studentService.createStudent(studentDTO);
    }

    private void assignCourseToStudent(StudentDTO studentDTO) {
        courseService.assignStudentToCourse(1L, studentDTO.getStudentId());
    }






}
