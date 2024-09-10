package com.example.main;

import dtos.ApiResponseDto;
import dtos.ApiResponseStatus;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import repository.UserRepository;
import services.UserServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class UserServiceImplTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserServiceImpl userService;

	@Test
	public void deleteUser_UserExists_Success() {
		openMocks(this);
		User user = new User();
		user.setId(1L);

		when(userRepository.findById(1L)).thenReturn(Optional.of(user));

		ResponseEntity<ApiResponseDto<?>> response = userService.deleteUser(1L);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ApiResponseStatus.SUCCESS.name(), response.getBody().getStatus());
		verify(userRepository, times(1)).delete(user);
	}

	@Test
	public void deleteUser_UserNotFound_Failure() {
		openMocks(this);

		when(userRepository.findById(1L)).thenReturn(Optional.empty());

		ResponseEntity<ApiResponseDto<?>> response = userService.deleteUser(1L);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals(ApiResponseStatus.FAIL.name(), response.getBody().getStatus());
	}
}
