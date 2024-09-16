package com.example.main.controller;

import com.example.main.dtos.ApiResponseDto;
import com.example.main.dtos.CreateCourseDto;
import com.example.main.modals.Course;
import com.example.main.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/course")
public class CoursesController {
    @Autowired
    private CourseService courseService;


    @GetMapping("/")
    public String viewCourses(Model model) {
        ApiResponseDto<?> responseDto = courseService.getAllCourses().getBody();
        List<Course> courses = (List<Course>) responseDto.getResponse();
        model.addAttribute("courses", courses);
        return "courses";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCourse(@PathVariable("id") Long id) {
        courseService.deleteById(id);
        return "redirect:/course/";
    }

    @PostMapping("/create")
    public String createCourse(@ModelAttribute CreateCourseDto createCourseDto) {
        courseService.createCourse(createCourseDto);
        return "redirect:/course/";
    }

    @GetMapping("/create")
    public String createCourseForm(Model model, @ModelAttribute CreateCourseDto createCourseDto) {
        model.addAttribute("createCourseDto", createCourseDto);
        return "createCourse";
    }
}
