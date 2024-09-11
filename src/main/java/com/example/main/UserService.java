package com.example.main;

import com.example.main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getByID(int ID){
        try {
            return MySqlDatabase.getByID(ID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public User createUser(User user) {
        return userRepository.save(user);
    }
}
