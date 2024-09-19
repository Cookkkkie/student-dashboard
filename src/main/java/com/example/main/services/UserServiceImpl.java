package com.example.main.services;

import com.example.main.Exceptions.UserAlreadyExistsException;
import com.example.main.Exceptions.UserNotFoundException;
import com.example.main.Exceptions.UserServiceLogicException;
import com.example.main.dtos.*;
import com.example.main.modals.UserMod;
import com.example.main.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<ApiResponseDto<?>> createUser(UserDetailsRequestDto newUserDetails)
            throws UserAlreadyExistsException, UserServiceLogicException {
        try {
            if (userRepository.findByEmail(newUserDetails.getEmail()).isPresent()) {
                throw new UserAlreadyExistsException("User already exists " + newUserDetails.getEmail());
            }
            UserMod newUser = new UserMod(
                    newUserDetails.getName(), newUserDetails.getLast_name(), newUserDetails.getEmail(),
                    newUserDetails.getPassword(), newUserDetails.getAccountStatus(), newUserDetails.getRole()
            );
            userRepository.save(newUser);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ApiResponseDto<>(ApiResponseStatus.SUCCESS.name(), "User created successfully"));
        } catch (UserAlreadyExistsException e) {
            throw new UserAlreadyExistsException(e.getMessage());
        } catch (Exception e) {
            log.error("Failed to create new user account: " + e.getMessage());
            throw new UserServiceLogicException();
        }
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> getAllUsers() throws UserServiceLogicException {
        try {
            List<UserMod> users = userRepository.findAllByOrderByUserID();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponseDto<>(ApiResponseStatus.SUCCESS.name(), users));
        } catch (Exception e) {
            log.error("Failed to get all users: " + e.getMessage(), e);
            throw new UserServiceLogicException();
        }
    }


    @Override
    public ResponseEntity<ApiResponseDto<?>> updateUser(UserDetailsRequestDto newUserDetails, String email)
            throws UserNotFoundException, UserServiceLogicException {
        try {
            UserMod user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException("User not found " + email));

            if (newUserDetails.getEmail() != null && !newUserDetails.getEmail().isEmpty()) {
                user.setEmail(newUserDetails.getEmail());
            }

            if (newUserDetails.getPassword() != null && !newUserDetails.getPassword().isEmpty()) {
                user.setPassword(newUserDetails.getPassword());
            }

            userRepository.save(user);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponseDto<>(ApiResponseStatus.SUCCESS.name(), "User updated successfully"));
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(e.getMessage());
        } catch (Exception e) {
            log.error("Failed to update user: " + e.getMessage());
            throw new UserServiceLogicException();
        }
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> getUserByEmail(String email) {
        try {
            Optional<UserMod> user = userRepository.findByEmail(email);
            return user.<ResponseEntity<ApiResponseDto<?>>>map(userMod -> ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponseDto<>(ApiResponseStatus.SUCCESS.name(), userMod))).orElseGet(() -> ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseDto<>(ApiResponseStatus.FAIL.name(), "User not found")));
        } catch (Exception e) {
            log.error("Failed to get user by email: {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDto<>(ApiResponseStatus.FAIL.name(), "Error fetching user"));
        }
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> softDeleteUser(String email) throws UserServiceLogicException, UserNotFoundException {
        try {
            UserMod user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            user.setAccountStatus(UserStatus.INACTIVE);
            userRepository.save(user);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponseDto<>(ApiResponseStatus.SUCCESS.name(), "User soft-deleted successfully"));

        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseDto<>(ApiResponseStatus.FAIL.name(), "User not found"));
        } catch (Exception e) {
            throw new UserServiceLogicException();
        }
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> createAdmin(String email) {
        try {
            Optional<UserMod> userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                UserMod user = userOptional.get();
                if (user.getRole() == UserRole.ADMIN) {
                    return ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(new ApiResponseDto<>(ApiResponseStatus.FAIL.name(), "Already have admin rules"));
                }
                user.setRole(UserRole.ADMIN);
                userRepository.save(user);
                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(new ApiResponseDto<>(ApiResponseStatus.SUCCESS.name(), "Admin created successfully"));
            } else {
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponseDto<>(ApiResponseStatus.FAIL.name(), "Admin not implemented"));
            }
        } catch (Exception e) {
            log.error("Failed to create admin: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDto<>(ApiResponseStatus.FAIL.name(), "Error creating admin"));
        }
    }
}
