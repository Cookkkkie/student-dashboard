package com.example.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;

@SpringBootApplication
@Controller
public class DashboardController {

	@RequestMapping("/main")
	public static void main(String[] args) throws SQLException {
		SpringApplication.run(DashboardController.class, args);
		MySqlDatabase.createTables();
	}

	@GetMapping("/dashboard")
	public String dashboardPage() {
		return "main";
	}


}
