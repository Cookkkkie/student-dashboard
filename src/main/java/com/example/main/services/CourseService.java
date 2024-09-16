package com.example.main.services;

import com.example.main.dtos.ApiResponseDto;
import com.example.main.dtos.ApiResponseStatus;
import com.example.main.dtos.CreateCourseDto;
import com.example.main.modals.Course;
import com.example.main.repository.CourseRepository;
import com.example.main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseRepository repo;
    @Autowired
    private UserRepository urepo;

    public void createCourse(CreateCourseDto createCourse){
        Course c = new Course();
        c.setName(createCourse.name);
        repo.save(c);
    }
    public ResponseEntity<ApiResponseDto<?>> getAllCourses() {
        try {
            List<Course> assignments = repo.findAll();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponseDto<>(ApiResponseStatus.SUCCESS.name(), assignments));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDto<>(ApiResponseStatus.FAIL.name(), "Error retrieving assignments"));
        }
    }
    public void deleteById(Long id) {
        if(repo.getCourseByCourseID(id) != null){
            repo.deleteById(id);
        }
    }
}
