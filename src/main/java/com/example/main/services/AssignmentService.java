package com.example.main.services;

import com.example.main.dtos.CreateAssignmentDto;
import com.example.main.modals.Assignment;
import com.example.main.modals.Course;
import com.example.main.repository.AssignmentRepository;
import com.example.main.dtos.ApiResponseDto;
import com.example.main.dtos.ApiResponseStatus;
import com.example.main.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private CourseRepository courseRepository;

    public void createAssignment(CreateAssignmentDto createAssignmentDto){
        Course course = courseRepository.getCourseByCourseID(createAssignmentDto.getCourseID());
        Assignment assignment = new Assignment(createAssignmentDto.assignmentName, createAssignmentDto.assignmentDate, createAssignmentDto.assignmentStatus, course);
        assignmentRepository.save(assignment);
    }

    public ResponseEntity<ApiResponseDto<?>> getAllAssignments() {
        try {
            List<Assignment> assignments = assignmentRepository.findAll();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponseDto<>(ApiResponseStatus.SUCCESS.name(), assignments));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDto<>(ApiResponseStatus.FAIL.name(), "Error retrieving assignments"));
        }
    }


    public void deleteById(Long id) {
        if (assignmentRepository.existsById(id)) {
            assignmentRepository.deleteById(id);
        }
    }
}
