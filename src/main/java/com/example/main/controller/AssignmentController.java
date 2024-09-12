package com.example.main.controller;

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
@RequestMapping("/view")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @PostMapping
    public ResponseEntity<ApiResponseDto<?>> createAssignment(@RequestBody Assignment assignment) {
        return assignmentService.createAssignment(assignment);
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<?>> getAllAssignments() {
        return assignmentService.getAllAssignments();
    }

    @GetMapping("/assignment")
    public String viewAssignments(Model model) {
        ApiResponseDto<?> responseDto = assignmentService.getAllAssignments().getBody();
        List<Assignment> assignments = (List<Assignment>) responseDto.getResponse();
        model.addAttribute("assignments", assignments);
        return "assignment";
    }
}
