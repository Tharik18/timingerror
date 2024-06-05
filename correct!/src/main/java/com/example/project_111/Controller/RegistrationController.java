package com.example.project_111.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project_111.Entity.Student;
import com.example.project_111.Entity.Teacher;
import com.example.project_111.Service.StudentService;
import com.example.project_111.Service.TeacherService;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/register/student")
    public ResponseEntity<String> registerStudent(@RequestBody Student student) {
        try {
            studentService.registerStudent(student);
            return ResponseEntity.ok("Student registered successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/register/teacher")
    public ResponseEntity<String> registerTeacher(@RequestBody Teacher teacher) {
        teacherService.registerTeacher(teacher);
        return ResponseEntity.ok("Teacher registered successfully!");
    }

    @GetMapping("/courses")
    public List<String> getCourses() {
        return studentService.getAllCourses();
    }

    @GetMapping("/teachers/{course}")
    public List<Teacher> getTeachersByCourse(@PathVariable String course) {
        return studentService.getTeachersByCourse(course);
    }

    @GetMapping("/availability/{teacherId}")
    public List<String> getAvailableTimeSlots(@PathVariable Long teacherId) {
        return studentService.getAvailableTimeSlots(teacherId);
    }

    // Other existing methods
}
