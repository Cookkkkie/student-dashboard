package com.example.main.services;

import com.example.main.Exceptions.UserAlreadyExistsException;
import com.example.main.Exceptions.UserNotFoundException;
import com.example.main.Exceptions.UserServiceLogicException;
import com.example.main.modals.UserMod;
import com.example.main.repository.UserRepository;
import com.example.main.dtos.ApiResponseDto;
import com.example.main.dtos.ApiResponseStatus;
import com.example.main.dtos.UserDetailsRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        try{
            if(userRepository.findByEmail(newUserDetails.getEmail()).isPresent()){
                throw new UserAlreadyExistsException("User already exists " + newUserDetails.getEmail());
            }
            UserMod newUser = new UserMod(
                    newUserDetails.getName(), newUserDetails.getLast_name(), newUserDetails.getEmail(), newUserDetails.getPassword()
            );
            userRepository.save(newUser);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ApiResponseDto<>(ApiResponseStatus.SUCCESS.name(), "User created successfully"));
        }catch (UserAlreadyExistsException e){
            throw new UserAlreadyExistsException(e.getMessage());
        }catch (Exception e){
            log.error("Failed to create new user account: " + e.getMessage());
            throw new UserServiceLogicException();
        }
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> getAllUsers()
            throws UserServiceLogicException {
        try{
            List<UserMod> users = userRepository.findAllByOrderByUserID();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponseDto<>(ApiResponseStatus.SUCCESS.name(), users)
                    );
        }catch (Exception e){
            log.error("Failed to get all users: " + e.getMessage());
            throw new UserServiceLogicException();
        }
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> updateUser(UserDetailsRequestDto newUserDetails, int id)
            throws UserNotFoundException, UserServiceLogicException {
        try {
            UserMod user = userRepository.findById((long) id).orElseThrow(() -> new UserNotFoundException("User not found" + id));


            if (newUserDetails.getEmail() !=null && !newUserDetails.getEmail().isEmpty())  {
                user.setEmail(newUserDetails.getEmail());
            }

            if (newUserDetails.getPassword() !=null && !newUserDetails.getPassword().isEmpty() ) {
                user.setPassword(newUserDetails.getPassword());
            }


            userRepository.save(user);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponseDto<>(ApiResponseStatus.SUCCESS.name(), "User updated successfully")
                    );
        }catch (UserNotFoundException e){
            throw new UserNotFoundException(e.getMessage());
        }catch (Exception e){
            log.error("Failed to update user: " + e.getMessage());
            throw new UserServiceLogicException();
        }
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> deleteUser(int id)
            throws UserServiceLogicException, UserNotFoundException {
        try{
            UserMod user = userRepository.findById((long) id).orElseThrow(() -> new UserNotFoundException("User not found"));

            userRepository.delete(user);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponseDto<>(ApiResponseStatus.SUCCESS.name(), "User deleted successfully")
                    );
        }catch (UserNotFoundException e){
            throw new UserNotFoundException(e.getMessage());
        }catch (Exception e){
            log.error("Failed to delete user: " + e.getMessage());
            throw new UserServiceLogicException();
        }
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> getUserByID(int id, String password) {
        try {
            UserMod user = userRepository.findById((long)id).orElseThrow(() -> new UserNotFoundException("Not found"));
            if(user.getPassword().equals(password)){
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto<>(ApiResponseStatus.SUCCESS.name(), user));
            }else{
                throw new UserNotFoundException("Wrong password");
            }
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        Optional<UserMod> user = userRepository.findByEmail(email);

        if(user.isPresent()) {
            var springUser = User.withUsername(user.get().getEmail())
                    .password(user.get().getPassword())
                    .build();

            return springUser;
        }

        return null;
    }
}