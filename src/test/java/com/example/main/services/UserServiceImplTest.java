package com.example.main.services;

import com.example.main.Exceptions.UserAlreadyExistsException;
import com.example.main.Exceptions.UserNotFoundException;
import com.example.main.Exceptions.UserServiceLogicException;
import com.example.main.dtos.*;
import com.example.main.modals.UserMod;
import com.example.main.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDetailsRequestDto userDetailsRequestDto;
    private UserMod userMod;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        userDetailsRequestDto = new UserDetailsRequestDto();
        userDetailsRequestDto.setEmail("newemail@example.com");
        userDetailsRequestDto.setPassword("newpassword");

        userMod = new UserMod();
        userMod.setEmail("oldemail@example.com");
        userMod.setPassword("oldpassword");
    }

    //createUser
    @Test
    public void testCreateUser_Success() throws UserAlreadyExistsException, UserServiceLogicException {
        // given
        UserDetailsRequestDto newUserDetails = new UserDetailsRequestDto();
        newUserDetails.setName("John");
        newUserDetails.setLast_name("Doe");
        newUserDetails.setEmail("john.doe@example.com");
        newUserDetails.setPassword("password");
        newUserDetails.setAccountStatus(UserStatus.ACTIVE);
        newUserDetails.setRole(UserRole.USER);

        when(userRepository.findByEmail(newUserDetails.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(UserMod.class))).thenReturn(new UserMod());

        // when
        ResponseEntity<ApiResponseDto<?>> response = userService.createUser(newUserDetails);

        // then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("SUCCESS", response.getBody().getStatus());
        assertEquals("User created successfully", response.getBody().getResponse());
        verify(userRepository, times(1)).save(any(UserMod.class));
    }

    @Test
    public void testCreateUser_ServiceLogicException() {
        // given
        UserDetailsRequestDto newUserDetails = new UserDetailsRequestDto();
        newUserDetails.setEmail("john.doe@example.com");

        when(userRepository.findByEmail(newUserDetails.getEmail())).thenThrow(new RuntimeException());

        // when
        assertThrows(UserServiceLogicException.class, () -> {
            userService.createUser(newUserDetails);
        });

        // then
        verify(userRepository, never()).save(any(UserMod.class));
    }

    @Test
    void createUser_UserAlreadyExists() {
        // given
        UserDetailsRequestDto newUserDetails = new UserDetailsRequestDto();

        when(userRepository.findByEmail(newUserDetails.getEmail())).thenReturn(Optional.of(new UserMod()));

        // when then
        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(newUserDetails));
    }

    //getAllUsers
    @Test
    public void testGetAllUsers_Success() throws UserServiceLogicException {
        // given
        UserMod user1 = new UserMod("John", "Doe", "john.doe@example.com", "password123", UserStatus.ACTIVE, UserRole.USER);
        user1.setUserID(1);

        UserMod user2 = new UserMod("Jane", "Doe", "jane.doe@example.com", "password456", UserStatus.ACTIVE, UserRole.USER);
        user2.setUserID(2);

        List<UserMod> users = Arrays.asList(user1, user2);
        when(userRepository.findAllByOrderByUserID()).thenReturn(users);

        // when
        ResponseEntity<ApiResponseDto<?>> response = userService.getAllUsers();

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ApiResponseStatus.SUCCESS.name(), response.getBody().getStatus());
        assertEquals(users, response.getBody().getResponse());
        verify(userRepository, times(1)).findAllByOrderByUserID();
    }

    @Test
    void getAllUsers_Exception() {
        // given
        when(userRepository.findAllByOrderByUserID()).thenThrow(new RuntimeException("Database error"));

        // when then
        assertThrows(UserServiceLogicException.class, () -> userService.getAllUsers());
    }

    //updateUser
    @Test
    void updateUser_Success() throws UserNotFoundException, UserServiceLogicException {
        // given
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(userMod));
        when(userRepository.save(any(UserMod.class))).thenReturn(userMod);

        ResponseEntity<ApiResponseDto<?>> response = userService.updateUser(userDetailsRequestDto, "oldemail@example.com");

        // when then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ApiResponseStatus.SUCCESS.name(), Objects.requireNonNull(response.getBody()).getStatus());
        assertEquals("User updated successfully", response.getBody().getResponse());

        verify(userRepository, times(1)).findByEmail(anyString());
        verify(userRepository, times(1)).save(any(UserMod.class));
    }

    @Test
    void updateUser_ServiceLogicException() {
        // given
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(userMod));
        doThrow(new RuntimeException("Database error")).when(userRepository).save(any(UserMod.class));

        // when then
        assertThrows(UserServiceLogicException.class, () -> {
            userService.updateUser(userDetailsRequestDto, "oldemail@example.com");
        });

        verify(userRepository, times(1)).findByEmail(anyString());
        verify(userRepository, times(1)).save(any(UserMod.class));
    }

    @Test
    void updateUser_UserNotFound() {
        // given
        String email = "john.doe@example.com";
        UserDetailsRequestDto newUserDetails = new UserDetailsRequestDto();
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // when then
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(newUserDetails, email));
    }


    //getUserByEmail
    @Test
    void testGetUserByEmail_UserFound() {
        // given
        String email = "oldemail@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userMod));

        // when
        ResponseEntity<ApiResponseDto<?>> response = userService.getUserByEmail(email);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ApiResponseStatus.SUCCESS.name(), response.getBody().getStatus());
        assertEquals(userMod, response.getBody().getResponse());
    }

    @Test
    void testGetUserByEmail_UserNotFound() {
        // given
        String email = "nonexistent@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // when
        ResponseEntity<ApiResponseDto<?>> response = userService.getUserByEmail(email);

        // then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ApiResponseStatus.FAIL.name(), response.getBody().getStatus());
        assertEquals("User not found", response.getBody().getResponse());
    }

    @Test
    void testGetUserByEmail_ExceptionThrown() {
        // given
        String email = "error@example.com";
        when(userRepository.findByEmail(email)).thenThrow(new RuntimeException("Database error"));

        // when
        ResponseEntity<ApiResponseDto<?>> response = userService.getUserByEmail(email);

        // then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(ApiResponseStatus.FAIL.name(), response.getBody().getStatus());
        assertEquals("Error fetching user", response.getBody().getResponse());
    }


    //softDeleteUser
    @Test
    public void testSoftDeleteUser_Success() throws UserServiceLogicException, UserNotFoundException {
        // given
        when(userRepository.findByEmail("oldemail@example.com")).thenReturn(Optional.of(userMod));

        ResponseEntity<ApiResponseDto<?>> response = userService.softDeleteUser("oldemail@example.com");

        // when then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User soft-deleted successfully", response.getBody().getResponse());
        assertEquals(UserStatus.INACTIVE, userMod.getAccountStatus());
        verify(userRepository, times(1)).save(userMod);
    }

    @Test
    public void testSoftDeleteUser_UserNotFound() throws UserNotFoundException, UserServiceLogicException {
        // given
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        ResponseEntity<ApiResponseDto<?>> response = userService.softDeleteUser("nonexistent@example.com");

        // when then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found", response.getBody().getResponse());
    }

    @Test
    public void testSoftDeleteUser_ServiceException() {
        // given
        when(userRepository.findByEmail("oldemail@example.com")).thenThrow(new RuntimeException());

        // when then
        assertThrows(UserServiceLogicException.class, () -> {
            userService.softDeleteUser("oldemail@example.com");
        });
    }

    //createAdmin
    @Test
    void testCreateAdmin_UserAlreadyAdmin() {
        // given
        userMod.setRole(UserRole.ADMIN);
        when(userRepository.findByEmail("oldemail@example.com")).thenReturn(Optional.of(userMod));

        ResponseEntity<ApiResponseDto<?>> response = userService.createAdmin("oldemail@example.com");

        // when then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Already have admin rules", response.getBody().getResponse());
    }

    @Test
    void testCreateAdmin_UserExistsAndNotAdmin() {
        // given
        userMod.setRole(UserRole.USER);
        when(userRepository.findByEmail("oldemail@example.com")).thenReturn(Optional.of(userMod));

        ResponseEntity<ApiResponseDto<?>> response = userService.createAdmin("oldemail@example.com");

        // when then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Admin created successfully", response.getBody().getResponse());
        verify(userRepository, times(1)).save(userMod);
    }

    @Test
    void testCreateAdmin_UserDoesNotExist() {
        // given
        when(userRepository.findByEmail("newemail@example.com")).thenReturn(Optional.empty());

        ResponseEntity<ApiResponseDto<?>> response = userService.createAdmin("newemail@example.com");

        // when then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Admin not implemented", response.getBody().getResponse());
    }

    @Test
    void testCreateAdmin_ExceptionThrown() {
        // given
        when(userRepository.findByEmail("newemail@example.com")).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<ApiResponseDto<?>> response = userService.createAdmin("newemail@example.com");

        // when then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error creating admin", response.getBody().getResponse());
    }
}
