package com.example.project_111.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project_111.Entity.Student;
import com.example.project_111.Entity.Teacher;
import com.example.project_111.Repository.StudentRepository;
import com.example.project_111.Repository.TeacherRepository;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    private static final DateTimeFormatter TIME_FORMATTER_24H = DateTimeFormatter.ofPattern("H:mm");
    private static final DateTimeFormatter TIME_FORMATTER_12H = DateTimeFormatter.ofPattern("h:mm a");

    public void save(Student student) {
        studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void changeStudentTiming(Long studentId, String newTiming) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isPresent() && isValidTime(newTiming)) {
            Student student = studentOpt.get();
            student.setTiming(newTiming);
            studentRepository.save(student);
        } else {
            throw new IllegalArgumentException("Invalid student ID or timing format");
        }
    }

    private boolean isValidTime(String time) {
        try {
            LocalTime.parse(time, TIME_FORMATTER_24H);
            return true;
        } catch (DateTimeParseException e) {
            try {
                LocalTime.parse(time, TIME_FORMATTER_12H);
                return true;
            } catch (DateTimeParseException ex) {
                return false;
            }
        }
    }

    public List<String> getAvailableTimeSlots(Long teacherId) {
        Optional<Teacher> teacher = teacherRepository.findById(teacherId);
        return teacher.map(Teacher::getAvailability).orElse(List.of());
    }

    public List<Teacher> getTeachersByCourse(String course) {
        return teacherRepository.findByCoursesContaining(course);
    }

    public List<String> getAllCourses() {
        return teacherRepository.findAll()
                .stream()
                .flatMap(teacher -> teacher.getCourses().stream())
                .distinct()
                .collect(Collectors.toList());
    }
}
