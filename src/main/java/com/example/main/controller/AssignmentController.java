package com.example.main.controller;

import com.example.main.Exceptions.UserNotFoundException;
import com.example.main.Exceptions.UserServiceLogicException;
import com.example.main.dtos.CreateAssignmentDto;
import com.example.main.modals.Assignment;
import com.example.main.modals.Course;
import com.example.main.modals.UserMod;
import com.example.main.repository.CourseRepository;
import com.example.main.repository.UserRepository;
import com.example.main.services.AssignmentService;
import com.example.main.dtos.ApiResponseDto;
import com.example.main.services.CourseService;
import com.example.main.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/assignment")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/")
    public String viewAssignments(Model model) throws UserNotFoundException, UserServiceLogicException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userID = auth.getName();

        ApiResponseDto<?> responseDto = assignmentService.getAssignmentsByUserId(Long.valueOf(userID)).getBody();
        if (responseDto != null && responseDto.getResponse() instanceof List) {
            @SuppressWarnings("unchecked")
            List<Assignment> assignments = (List<Assignment>) responseDto.getResponse();

            // Check if assignments are overdue
            LocalDate now = LocalDate.now();
            for (Assignment assignment : assignments) {
                boolean isOverdue = assignment.getDueDate().isBefore(now);
                assignment.setStatus(isOverdue ? "Overdue" : "Not overdue");
            }

            model.addAttribute("assignments", assignments);
        } else {
            model.addAttribute("assignments", List.of());
        }

        return "assignment";
    }



    @DeleteMapping("/delete/{id}")
    public String deleteAssignment(@PathVariable("id") Long id) {
        assignmentService.deleteById(id);
        return "redirect:/assignment/";
    }

    @PostMapping("/create")
    public String createAssignment(@ModelAttribute CreateAssignmentDto createAssignmentDto) throws UserNotFoundException, UserServiceLogicException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userID = auth.getName();
        if(!userRepository.findByUserID(Long.parseLong(userID)).isPresent()){
            throw new UserNotFoundException("User with ID " + userID + " doesn't exist");
        }
        UserMod u = userRepository.findByUserID(Long.parseLong(userID)).get();
        Course course = courseRepository.getCourseByCourseID(createAssignmentDto.getCourseID());
        if((courseRepository.getCourseByCourseID(createAssignmentDto.getCourseID()).getUser().getUserID() == Integer.parseInt(userID))){

            if (course != null) {
                assignmentService.createAssignment(createAssignmentDto);
            }
        }else{
            throw new RuntimeException("Course and userID mismatch");
        }


        return "redirect:/assignment/";
    }


    @GetMapping("/create")
    public String createAssignmentForm(Model model, @ModelAttribute CreateAssignmentDto createAssignmentDto) {
        model.addAttribute("createAssignmentDto", createAssignmentDto);
        return "create";
    }
}
