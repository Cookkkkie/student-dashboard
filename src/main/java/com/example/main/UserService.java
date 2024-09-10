package com.example.main;

import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class UserService {


    public User getByID(int ID){
        try {
            return MySqlDatabase.getByID(ID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
