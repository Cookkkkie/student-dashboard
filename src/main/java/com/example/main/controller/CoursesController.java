package com.example.main.controller;

import com.example.main.dtos.ApiResponseDto;
import com.example.main.dtos.CreateAssignmentDto;
import com.example.main.dtos.CreateCourseDto;
import com.example.main.modals.Assignment;
import com.example.main.modals.Course;
import com.example.main.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class CoursesController {
    @Autowired
    private CourseService service;
   /* @GetMapping
    public ResponseEntity<ApiResponseDto<?>> getAllAssignments() {
        return service.getAllCourses();
    }*/

    @GetMapping("/courses")
    public String viewCourses(Model model) {
        ApiResponseDto<?> responseDto = service.getAllCourses().getBody();
        List<Course> courses = (List<Course>) responseDto.getResponse();
        model.addAttribute("courses", courses);
        return "courses";
    }

    @DeleteMapping("courses/delete/{id}")
    public String deleteCourse(@PathVariable("id") long id) {
        service.deleteById(id);
        return "redirect:/courses";
    }

    @PostMapping("/course/create")
    public String createCourse(@ModelAttribute CreateCourseDto createCourseDto) {
        service.createCourse(createCourseDto);
        return "redirect:/courses";
    }

    @GetMapping("/course/create")
    public String createCourseForm(Model model, @ModelAttribute CreateCourseDto createCourseDto) {
        model.addAttribute("createCourseDto", createCourseDto);
        return "createCourse"; // Do not include '.html'
    }
}
