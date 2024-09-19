package com.example.main.services;

import com.example.main.dtos.ApiResponseDto;
import com.example.main.dtos.ApiResponseStatus;
import com.example.main.modals.Task;
import com.example.main.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TodoService todoService;

    @Test
    void getTasksByUserId_Success() {
        // given
        Long userId = 1L;
        List<Task> mockTasks = Arrays.asList(new Task(), new Task());

        when(taskRepository.findByUserUserID(userId)).thenReturn(mockTasks);

        // when
        ResponseEntity<ApiResponseDto<List<Task>>> response = todoService.getTasksByUserId(userId);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ApiResponseStatus.SUCCESS.name(), response.getBody().getStatus());
        assertEquals(mockTasks, response.getBody().getResponse());
        verify(taskRepository, times(1)).findByUserUserID(userId);
    }

    @Test
    void testGetTasksByUserId_Failure() {
        // given
        Long userId = 1L;

        when(taskRepository.findByUserUserID(userId)).thenThrow(new RuntimeException("Database error"));

        // when
        ResponseEntity<ApiResponseDto<List<Task>>> response = todoService.getTasksByUserId(userId);

        // then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(ApiResponseStatus.FAIL.name(), response.getBody().getStatus());
        assertEquals(null, response.getBody().getResponse());
    }

    @Test
    void testSaveTask() {
        // given
        Task task = new Task();

        // when
        todoService.saveTask(task);

        // then
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testDeleteTaskById_TaskExists() {
        // given
        Long id = 1L;
        when(taskRepository.existsById(id)).thenReturn(true);

        // when
        todoService.deleteTaskById(id);

        // then
        verify(taskRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteTaskById_TaskDoesNotExist() {
        // given
        Long id = 1L;
        when(taskRepository.existsById(id)).thenReturn(false);

        // when
        todoService.deleteTaskById(id);

        // then
        verify(taskRepository, times(0)).deleteById(id);
    }
}
