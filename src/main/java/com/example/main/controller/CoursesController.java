package com.example.main.controller;

import com.example.main.dtos.ApiResponseDto;
import com.example.main.dtos.CreateCourseDto;
import com.example.main.modals.Course;
import com.example.main.services.CourseService;
import jakarta.servlet.http.HttpSession;
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
    public String viewCourses(Model model, HttpSession session) {
//        System.out.println(session.getAttribute("userID").toString());
        ApiResponseDto<?> responseDto = courseService.getAllCourses(Long.parseLong(session.getAttribute("userID").toString())).getBody();
        if (responseDto != null && responseDto.getResponse() instanceof List) {
            @SuppressWarnings("unchecked")
            List<Course> courses = (List<Course>) responseDto.getResponse();
            model.addAttribute("courses", courses);
        } else {
            model.addAttribute("courses", List.of());
        }
        return "courses";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCourse(@PathVariable("id") Long id) {
        courseService.deleteById(id);
        return "redirect:/course/";
    }

    @PostMapping("/create")
    public String createCourse(@ModelAttribute CreateCourseDto createCourseDto, HttpSession session) {
        courseService.createCourse(createCourseDto, Long.parseLong(session.getAttribute("userID").toString()));
        return "redirect:/course/";
    }

    @GetMapping("/create")
    public String createCourseForm(Model model, @ModelAttribute CreateCourseDto createCourseDto) {
        model.addAttribute("createCourseDto", createCourseDto);
        return "createCourse";
    }
}
