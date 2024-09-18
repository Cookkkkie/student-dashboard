package com.example.main.services;

import com.example.main.dtos.ApiResponseDto;
import com.example.main.dtos.ApiResponseStatus;
import com.example.main.dtos.CreateAssignmentDto;
import com.example.main.modals.Assignment;
import com.example.main.modals.Course;
import com.example.main.repository.AssignmentRepository;
import com.example.main.repository.CourseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

class AssignmentServiceTest {

    @Mock
    private AssignmentRepository assignmentRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private AssignmentService assignmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAssignment() {
        // given
        CreateAssignmentDto createAssignmentDto = new CreateAssignmentDto(
                "Test Assignment",
                "PENDING",
                "2024-09-18",
                1L
        );

        Course course = new Course();
        course.setCourseID(1L);

        when(courseRepository.getCourseByCourseID(1L)).thenReturn(course);

        // when
        assignmentService.createAssignment(createAssignmentDto);

        // then
        verify(assignmentRepository, times(1)).save(any(Assignment.class));
    }

    @Test
    void testGetAssignmentsByUserIdSuccess() {
        // given
        Long userId = 1L;
        List<Assignment> assignments = Collections.singletonList(new Assignment());
        when(assignmentRepository.findByCourse_UserUserID(userId)).thenReturn(assignments);

        // when
        ResponseEntity<ApiResponseDto<?>> response = assignmentService.getAssignmentsByUserId(userId);

        // then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(ApiResponseStatus.SUCCESS.name(), response.getBody().getStatus());
        Assertions.assertEquals(assignments, response.getBody().getResponse());
    }

    @Test
    void testGetAssignmentsByUserIdFailure() {
        // given
        Long userId = 1L;
        when(assignmentRepository.findByCourse_UserUserID(userId)).thenThrow(new RuntimeException("Database error"));

        // when
        ResponseEntity<ApiResponseDto<?>> response = assignmentService.getAssignmentsByUserId(userId);

        // then
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Assertions.assertEquals(ApiResponseStatus.FAIL.name(), response.getBody().getStatus());
        Assertions.assertEquals("Error retrieving assignments", response.getBody().getResponse());
    }

    @Test
    public void testDeleteById() {
        // given
        Long id = 1L;

        when(assignmentRepository.existsById(id)).thenReturn(true);

        // when
        assignmentService.deleteById(id);

        // then
        verify(assignmentRepository, times(1)).deleteById(id);
    }
}