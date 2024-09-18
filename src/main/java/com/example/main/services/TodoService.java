package com.example.main.services;

import com.example.main.dtos.ApiResponseDto;
import com.example.main.dtos.ApiResponseStatus;
import com.example.main.modals.Task;
import com.example.main.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService {

    @Autowired
    private TaskRepository taskRepository;

    public ResponseEntity<ApiResponseDto<List<Task>>> getTasksByUserId(Long userId) {
        try {
            List<Task> tasks = taskRepository.findByUserUserID(userId);  // Assuming this method is correct
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponseDto<>(ApiResponseStatus.SUCCESS.name(), tasks));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDto<>(ApiResponseStatus.FAIL.name(), null));
        }
    }

    public void saveTask(Task task) {
        taskRepository.save(task);
    }

    public void deleteTaskById(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
        }
    }

    public ResponseEntity<ApiResponseDto<List<Task>>> getTopDueSoonTasks(Long userId) {
        try {
            LocalDate today = LocalDate.now();
            LocalDate soonDue = today.plusDays(7); // Adjust this range as needed

            List<Task> tasks = taskRepository.findByUserUserIDAndDueDateBetween(
                    userId, today, soonDue);

            // Sort by due date and limit to 3
            tasks = tasks.stream()
                    .sorted(Comparator.comparing(Task::getDueDate))
                    .limit(3)
                    .collect(Collectors.toList());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponseDto<>(ApiResponseStatus.SUCCESS.name(), tasks));
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponseDto<>(ApiResponseStatus.FAIL.name(), null), HttpStatus.INTERNAL_SERVER_ERROR);
//            return ResponseEntity
//                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ApiResponseDto<>(ApiResponseStatus.FAIL.name(), "Error retrieving tasks"));
        }
    }
}
