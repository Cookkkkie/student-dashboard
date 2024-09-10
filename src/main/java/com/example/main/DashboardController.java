package com.example.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class DashboardController {

	public static void main(String[] args) throws SQLException {
		SpringApplication.run(DashboardController.class, args);
		DATABASE.main(args);
	}    

}
