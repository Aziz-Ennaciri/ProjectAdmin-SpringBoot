package com.adminproject.admin.Web;

import com.adminproject.admin.DTOs.CourseDTO;
import com.adminproject.admin.DTOs.InstructorDTO;
import com.adminproject.admin.Entities.User;
import com.adminproject.admin.Services.ICourseService;
import com.adminproject.admin.Services.IInstructorService;
import com.adminproject.admin.Services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instructors")
public class InstructorRestController {
    @Autowired
    private IInstructorService instructorService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ICourseService courseService;

    @GetMapping
    public Page<InstructorDTO> searchInstructors(@RequestParam(name = "keyword",defaultValue = "")String keyword,
                                                 @RequestParam(name = "page",defaultValue = "0")int page,
                                                 @RequestParam(name = "size",defaultValue = "5")int size){
        return instructorService.findInstructorsByName(keyword,page,size);
    }

    @GetMapping("/all")
    public List<InstructorDTO> findAllInstructors(){
        return instructorService.fetchInstructors();
    }

    @DeleteMapping("/{instructorId}")
    public void deleteInstructor(@PathVariable Long instructorId){
        instructorService.removeInstructor(instructorId);
    }

    @PostMapping
    public InstructorDTO saveInstructor(@RequestBody InstructorDTO instructorDTO){
        User user = userService.loadUserByEmail(instructorDTO.getUserDTO().getEmail());
        if (user!=null) throw new RuntimeException("Email Already Exist");
        return instructorService.createInstructor(instructorDTO);
    }

    @PutMapping("/{instructorId}")
    public InstructorDTO updateInstructor(@RequestBody InstructorDTO instructorDTO,@PathVariable Long instructorId){
        instructorDTO.setInstructorId(instructorId);
        return instructorService.updateInstructor(instructorDTO);
    }

    @GetMapping("/{instructorId}/courses")
    public Page<CourseDTO> coursesByInstructor(@PathVariable Long instructorId,
                                               @RequestParam(name = "page",defaultValue = "0")int page,
                                               @RequestParam(name = "size",defaultValue = "5")int size){
        return courseService.fetchCoursesForInstructor(instructorId,page,size);
    }

    @GetMapping("/find")
    public InstructorDTO loadInstructorByEmail(@RequestParam(name = "email",defaultValue = "")String email){
        return instructorService.loadInstructorByEmail(email);
    }
}
