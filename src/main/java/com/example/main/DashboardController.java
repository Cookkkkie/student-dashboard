package com.example.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.SQLException;

@SpringBootApplication
@ComponentScan(basePackages = "services")
@ComponentScan(basePackages = "com.example.main")
public class DashboardController {

	public static void main(String[] args) throws SQLException {
		SpringApplication.run(DashboardController.class, args);
//		MySqlDatabase.createTables();
	}
}
