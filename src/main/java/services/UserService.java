package services;

import dtos.ApiResponseDto;
import exceptions.UserNotFoundException;
import exceptions.UserServiceLogicException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    ResponseEntity<ApiResponseDto<?>> deleteUser(int id)
            throws UserNotFoundException, UserServiceLogicException;
}