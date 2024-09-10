package com.example.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.sql.SQLException;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "repository")
@EntityScan(basePackages = "com.example.main")
public class DashboardController {

	public static void main(String[] args) {
		SpringApplication.run(DashboardController.class, args);
        try {
            MySqlDatabase.createTables();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
