package com.adminproject.admin.Web;

import com.adminproject.admin.DTOs.CourseDTO;
import com.adminproject.admin.DTOs.StudentDTO;
import com.adminproject.admin.Entities.User;
import com.adminproject.admin.Services.ICourseService;
import com.adminproject.admin.Services.IStudentService;
import com.adminproject.admin.Services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentRestController {

    @Autowired
    private IStudentService studentService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ICourseService courseService;

    @GetMapping
    public Page<StudentDTO> searchStudents(@RequestParam(name = "keyword",defaultValue = "")String keyword,
                                           @RequestParam(name = "page",defaultValue = "0")int page,
                                           @RequestParam(name = "size",defaultValue = "5")int size){
        return studentService.loadStudentsByName(keyword,page,size);
    }

    @DeleteMapping("/{studentId}")
    public void deleteStudent(@PathVariable Long studentId){
        studentService.removeStudent(studentId);
    }

    @PostMapping
    public StudentDTO saveStudent(@RequestBody StudentDTO studentDTO){
        User user = userService.loadUserByEmail(studentDTO.getUserDto().getEmail());
        if (user!=null) throw new RuntimeException("Email Already Exist");
        return studentService.createStudent(studentDTO);
    }

    @PutMapping("/{studentId}")
    public StudentDTO updateStudent(@RequestBody StudentDTO studentDTO,@PathVariable Long studentId){
        studentDTO.setStudentId(studentId);
        return studentService.updateStudent(studentDTO);
    }

    @GetMapping("/{studentId}/courses")
    public Page<CourseDTO> coursesByStudentId(@PathVariable Long studentId,
                                              @RequestParam(name = "page",defaultValue = "0")int page,
                                              @RequestParam(name = "size",defaultValue = "5")int size){
        return courseService.fetchCoursesForStudent(studentId,page,size);
    }

    @GetMapping("/{studentId}/other-courses")
    public Page<CourseDTO> nonSubscribedCoursesByStudentId(@PathVariable Long studentId,
                                                           @RequestParam(name = "page",defaultValue = "0")int page,
                                                           @RequestParam(name = "size",defaultValue = "5")int size){
        return courseService.fetchNonEnrolledInCoursesForStudent(studentId,page,size);
    }

    @GetMapping("/find")
    public StudentDTO loadStudentsByEmail(@RequestParam(name = "email",defaultValue = "")String email){
        return studentService.loadStudentByEmail(email);
    }
}
