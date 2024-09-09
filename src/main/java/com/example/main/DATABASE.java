package com.example.main;

import java.sql.*;

public class DATABASE {
    private static final String url = "jdbc:sqlite:database.db";
    private Connection conn;
    public static void main(String[] args) throws SQLException {

        Connection connection = DriverManager.getConnection(url);
        Statement statement = connection.createStatement();

        String createUsers = "CREATE TABLE IF NOT EXISTS users(" +
                "    userID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "    name VARCHAR(100) NOT NULL," +
                "    lastName VARCHAR(100) NOT NULL," +
                "    email VARCHAR(255) NOT NULL UNIQUE," +
                "    password VARCHAR(255) NOT NULL);";

        String createCourse = "CREATE TABLE IF NOT EXISTS course(" +
                "    courseID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "    name VARCHAR(100) NOT NULL," +
                "    userID INT," +
                "    FOREIGN KEY (userID) REFERENCES users(userID) ON DELETE CASCADE" +
                ");";

        String createAssignments = "CREATE TABLE IF NOT EXISTS assignments (" +
                "    assignmentID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "    name VARCHAR(100) NOT NULL," +
                "    dueDate DATE NOT NULL," +
                "    status BOOLEAN," +
                "    courseID INT," +
                "    FOREIGN KEY (courseID) REFERENCES course(courseID) ON DELETE CASCADE" +
                ");";

        String createToDoList = "CREATE TABLE IF NOT EXISTS ToDoList (" +
                "    listID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "    name VARCHAR(100) NOT NULL," +
                "    content TEXT," +
                "    status BOOLEAN," +
                "    due_date DATE," +
                "    userID INT," +
                "    FOREIGN KEY (userID) REFERENCES users(userID) ON DELETE CASCADE" +
                ");";

        statement.execute(createUsers);
        statement.execute(createCourse);
        statement.execute(createAssignments);
        statement.execute(createToDoList);

    }


    public void addUser(String name, String lastName, String email, String password) throws SQLException {
        String addUser = "INSERT INTO users(name, lastName, email, password) VALUES(?,?,?,?);";
        conn = DriverManager.getConnection(url);
        PreparedStatement statement = conn.prepareStatement(addUser);

        statement.setString(1, name);
        statement.setString(2, lastName);
        statement.setString(3, email);
        statement.setString(4, password);

        statement.executeUpdate();
    }

    public void readUser(String email) throws SQLException {
        String readUser = "SELECT * FROM users WHERE email = ?;";
        conn = DriverManager.getConnection(url);
        PreparedStatement statement = conn.prepareStatement(readUser);
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString("name") + " " + resultSet.getString("lastName") + " " + resultSet.getString("email"));
        }

    }

    public void readCourse() {
    }

    public void readAssignment() {
    }

    public void readToDo() {
    }


}
