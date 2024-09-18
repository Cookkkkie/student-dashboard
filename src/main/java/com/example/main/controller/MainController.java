package com.example.main.controller;

import com.example.main.Exceptions.UserNotFoundException;
import com.example.main.Exceptions.UserServiceLogicException;
import com.example.main.dtos.ApiResponseDto;
import com.example.main.modals.Assignment;
import com.example.main.modals.Course;
import com.example.main.services.AssignmentService;
import com.example.main.services.CourseService;
import com.example.main.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/main")
public class MainController {

    @Autowired
    private TodoService todoService;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/dashboard")
    public String dashboardPage(Model model) throws UserNotFoundException, UserServiceLogicException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userID = auth.getName();

        //-------------------------------------TODO LIST -----------------------------------------------
        ApiResponseDto<?> tasksResponse = todoService.getTopDueSoonTasks(Long.valueOf(userID)).getBody();
        if (tasksResponse != null && tasksResponse.getResponse() instanceof List) {
            @SuppressWarnings("unchecked")
            List<Task> tasks = (List<Task>) tasksResponse.getResponse();
            model.addAttribute("tasksDueSoon", tasks);
        } else {
            model.addAttribute("tasksDueSoon", List.of());
        }
        //-----------------------------------------------------------------------------------------------

        //-------------------------------------ASSIGNMENTS DUE SOON -----------------------------------
        ApiResponseDto<?> assignmentsResponse = assignmentService.getTopDueSoonAssignments(Long.valueOf(userID)).getBody();
        if (assignmentsResponse != null && assignmentsResponse.getResponse() instanceof List) {
            @SuppressWarnings("unchecked")
            List<Assignment> assignments = (List<Assignment>) assignmentsResponse.getResponse();
            model.addAttribute("assignmentsDueSoon", assignments);
        } else {
            model.addAttribute("assignmentsDueSoon", List.of());
        }
        //-----------------------------------------------------------------------------------------------

        //-------------------------------------COURSES WITH MOST ASSIGNMENTS -----------------------------------
        ApiResponseDto<?> coursesResponse = courseService.getAllCourses(Long.valueOf(userID)).getBody();
        if (coursesResponse != null && coursesResponse.getResponse() instanceof List) {
            @SuppressWarnings("unchecked")
            List<Course> courses = (List<Course>) coursesResponse.getResponse();
            // Sort courses by the number of assignments
            List<Course> topCourses = courses.stream()
                    .sorted((c1, c2) -> Integer.compare(c2.getAssignments().size(), c1.getAssignments().size()))
                    .limit(3)
                    .toList();
            model.addAttribute("topCourses", topCourses);
        } else {
            model.addAttribute("topCourses", List.of());
        }
        //-----------------------------------------------------------------------------------------------

        return "main";
    }
}
