package com.example.main.controller;

import com.example.main.dtos.CreateAssignmentDto;
import com.example.main.modals.Assignment;
import com.example.main.services.AssignmentService;
import com.example.main.dtos.ApiResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/assignment")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @GetMapping
    public ResponseEntity<ApiResponseDto<?>> getAllAssignments() {
        return assignmentService.getAllAssignments();
    }

    @GetMapping("/")
    public String viewAssignments(Model model) {
        ApiResponseDto<?> responseDto = assignmentService.getAllAssignments().getBody();
        if (responseDto != null && responseDto.getResponse() instanceof List) {
            @SuppressWarnings("unchecked")
            List<Assignment> assignments = (List<Assignment>) responseDto.getResponse();
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
    public String createAssignment(@ModelAttribute CreateAssignmentDto createAssignmentDto) {
        assignmentService.createAssignment(createAssignmentDto);
        return "redirect:/assignment/";
    }

    @GetMapping("/create")
    public String createAssignmentForm(Model model, @ModelAttribute CreateAssignmentDto createAssignmentDto) {
        model.addAttribute("createAssignmentDto", createAssignmentDto);
        return "create";
    }
}
