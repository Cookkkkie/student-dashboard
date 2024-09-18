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
        //System.out.println("Email"+email);
        //System.out.println("UserID"+session.getAttribute("userID").toString());

//        UserMod user = userRepository.getById(Long.valueOf(userID));
        ApiResponseDto<?> responseDto = assignmentService.getAssignmentsByUserId(Long.valueOf(userID)).getBody();
        if (responseDto != null && responseDto.getResponse() instanceof List) {
            @SuppressWarnings("unchecked")
            List<Assignment> assignments = (List<Assignment>) responseDto.getResponse();
            for(Assignment assignment : assignments) {
                System.out.println(assignment.getName());
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

        UserMod u = userRepository.findByUserID(Long.parseLong(userID)).get();
       // assert userResponse != null;
       // UserMod user = (UserMod) userResponse.getResponse();
//        System.out.println(user.getUserID());
        Course course = courseRepository.getCourseByCourseID(createAssignmentDto.getCourseID());
        if((courseRepository.getCourseByCourseID(createAssignmentDto.getCourseID()).getUser().getUserID() == Integer.parseInt(userID))){

            if (course != null) {
                assignmentService.createAssignment(createAssignmentDto);
            }
        }else{
            System.out.printf("WRONG ID\n");
        }


        return "redirect:/assignment/";
    }


    @GetMapping("/create")
    public String createAssignmentForm(Model model, @ModelAttribute CreateAssignmentDto createAssignmentDto) {
        model.addAttribute("createAssignmentDto", createAssignmentDto);
        return "create";
    }
}
