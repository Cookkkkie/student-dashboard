package services;

import com.example.main.User;
import dtos.ApiResponseDto;
import dtos.ApiResponseStatus;
import exceptions.UserNotFoundException;
import exceptions.UserServiceLogicException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import repository.UserRepository;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> deleteUser(long id) throws UserServiceLogicException {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id " + id));

            userRepository.delete(user);

            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto<>(ApiResponseStatus.SUCCESS.name(), "User account deleted successfully!"));
        } catch (UserNotFoundException e) {
            log.error("User not found: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseDto<>(ApiResponseStatus.FAIL.name(), e.getMessage()));
        } catch (Exception e) {
            log.error("Failed to delete user account: " + e.getMessage());
            throw new UserServiceLogicException("An error occurred while deleting the user account.");
        }
    }
}
