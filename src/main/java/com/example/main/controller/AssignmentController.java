package com.example.main.controller;

import com.example.main.modals.Assignment;
import services.AssignmentService;
import dtos.ApiResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assignments")
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
}
