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
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;

    public void createCourse(CreateCourseDto createCourse){
        Course c = new Course();
        c.setName(createCourse.name);
        courseRepository.save(c);
    }
    public void createCourse(CreateCourseDto createCourse, long userID){
        Course c = new Course();
        c.setName(createCourse.name);
        c.setUser(userRepository.findById(userID).get());
        courseRepository.save(c);
    }
    public ResponseEntity<ApiResponseDto<?>> getAllCourses() {
        try {
            List<Course> courses = courseRepository.findAll();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponseDto<>(ApiResponseStatus.SUCCESS.name(), courses));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDto<>(ApiResponseStatus.FAIL.name(), "Error retrieving courses"));
        }
    }
    public ResponseEntity<ApiResponseDto<?>> getAllCourses(long ID) {
        try {
            List<Course> courses = courseRepository.findAll();
            List<Course> truecourselist = courses.stream().filter( (c) -> {return c.getUser().getUserID() == ID;}).toList();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponseDto<>(ApiResponseStatus.SUCCESS.name(), truecourselist));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDto<>(ApiResponseStatus.FAIL.name(), "Error retrieving courses"));
        }
    }
    public void deleteById(Long id) {
        if(courseRepository.getCourseByCourseID(id) != null){
            courseRepository.deleteById(id);
        }
    }
}
