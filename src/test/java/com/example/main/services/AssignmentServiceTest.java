package com.example.main.services;

import com.example.main.dtos.CreateAssignmentDto;
import com.example.main.modals.Assignment;
import com.example.main.modals.Course;
import com.example.main.repository.AssignmentRepository;
import org.apache.catalina.Session;
import org.apache.catalina.Store;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


@Nested
class AssignmentServiceTest {

    @Test
    void createAssignment() {

        var createAssignmentDto = new CreateAssignmentDto(
                "Maths project",
                "urgent",
                "15.01.2025",
                1L);

        var course = new Course();

        var assignmentService = new AssignmentService();
        assignmentService.createAssignment(createAssignmentDto);

        assertEquals("Maths project", createAssignmentDto.assignmentName);
        assertEquals("urgent", createAssignmentDto.assignmentStatus);
        assertEquals("15.01.2025", createAssignmentDto.assignmentDate);
        assertEquals(1L, createAssignmentDto.courseID);
    }
    }




//    @Test
//    void getAllAssignments() {
//
//    }
//
//    @Test
//    void deleteById() {
//
//    }
}