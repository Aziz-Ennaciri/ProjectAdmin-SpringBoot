package com.adminproject.admin.Services.impl;

import com.adminproject.admin.DAO.StudentRepository;
import com.adminproject.admin.DTOs.StudentDTO;
import com.adminproject.admin.Entities.Course;
import com.adminproject.admin.Entities.Student;
import com.adminproject.admin.Entities.User;
import com.adminproject.admin.Mappers.StudentMapper;
import com.adminproject.admin.Services.IStudentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.stream.Collectors;

@Transactional
@Service
public class StudentServiceImpl implements IStudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private UserServiceImpl userService;
    @Override
    public Student loadStudent(Long studentId) {
        return studentRepository.findById(studentId).orElseThrow(()->new EntityNotFoundException("Student With This ID:"+studentId+"Not Found"));
    }

    @Override
    public Page<StudentDTO> loadStudentsByName(String name, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<Student> studentsPage =studentRepository.findStudentsByName(name,pageRequest);
        return new PageImpl<>(studentsPage.getContent().stream().map(student -> studentMapper.fromStudent(student)).collect(Collectors.toList()),pageRequest,studentsPage.getTotalElements());
    }

    @Override
    public StudentDTO loadStudentByEmail(String email) {
        return studentMapper.fromStudent(studentRepository.findStudentByEmail(email));
    }

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        User user = userService.createUser(studentDTO.getUserDto().getEmail(),studentDTO.getUserDto().getPassword());
        userService.assignRoleToUser(user.getEmail(),"student");
        Student student = studentMapper.fromStudentDTO(studentDTO);
        student.setUser(user);
        Student saveStudent = studentRepository.save(student);
        return studentMapper.fromStudent(saveStudent);
    }

    @Override
    public StudentDTO updateStudent(StudentDTO studentDTO) {
        Student loadStudent = loadStudent(studentDTO.getStudentId());
        Student student = studentMapper.fromStudentDTO(studentDTO);
        student.setUser(loadStudent.getUser());
        student.setCourses(loadStudent.getCourses());
        Student updateStudent = studentRepository.save(student);
        return studentMapper.fromStudent(updateStudent);
    }

    @Override
    public void removeStudent(Long studentId) {
        Student student =loadStudent(studentId);
        Iterator<Course> courseIterator = student.getCourses().iterator();
        if (courseIterator.hasNext()){
            Course course = courseIterator.next();
            course.removeStudentFromCourse(student);
        }
        studentRepository.deleteById(studentId);
    }
}
