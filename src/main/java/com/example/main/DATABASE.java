package com.example.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DATABASE {

    public static void main(String[] args) {
        try (
                Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
                Statement statement = connection.createStatement()
        ) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS users(" +
                    "    userID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "    name VARCHAR(100) NOT NULL," +
                    "    lastName VARCHAR(100) NOT NULL," +
                    "    email VARCHAR(255) NOT NULL UNIQUE," +
                    "    password VARCHAR(255) NOT NULL);";
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace(System.err);

        }


        try (
                Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
                Statement statement = connection.createStatement()
        ) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS course(" +
                    "    courseID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "    name VARCHAR(100) NOT NULL," +
                    "    userID INT," +
                    "    FOREIGN KEY (userID) REFERENCES users(userID) ON DELETE CASCADE" +
                    ");";
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace(System.err);

        }



        try (
                Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
                Statement statement = connection.createStatement()
        ) {
            String createTableSQL = "CREATE TABLE assignments (" +
                    "    assignmentID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "    name VARCHAR(100) NOT NULL," +
                    "    dueDate DATE NOT NULL," +
                    "    status BOOLEAN," +
                    "    courseID INT," +
                    "    FOREIGN KEY (courseID) REFERENCES course(courseID) ON DELETE CASCADE" +
                    ");";
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace(System.err);

        }




        try (
                Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
                Statement statement = connection.createStatement()
        ) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS toDoList (" +
                    "    listID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "    name VARCHAR(100) NOT NULL," +
                    "    content TEXT," +
                    "    status BOOLEAN," +
                    "    due_date DATE," +
                    "    userID INT," +
                    "    FOREIGN KEY (userID) REFERENCES users(userID) ON DELETE CASCADE" +
                    ");";
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace(System.err);

        }


    }


}
