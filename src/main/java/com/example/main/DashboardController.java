package com.example.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;
import java.util.TimeZone;

@SpringBootApplication
@Controller
@RequestMapping("/")
public class DashboardController implements ErrorController {

	@RequestMapping("/")
	public static void main(String[] args) throws SQLException {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		SpringApplication.run(DashboardController.class, args);
		MySqlDatabase.createTables();
	}
	@RequestMapping("/error")
	public String redirect(){
		return "redirect:/main/dashboard";
	}

}
