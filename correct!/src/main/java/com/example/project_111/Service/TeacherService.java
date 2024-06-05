package com.example.project_111.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project_111.Entity.Teacher;
import com.example.project_111.Repository.TeacherRepository;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    public void save(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    public List<String> getAvailabilityByTeacher(Long teacherId) {
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
