//package com.example.main;
//
//import org.springframework.stereotype.Component;
//
//import java.sql.*;
//
//@Component
//public class DATABASE {
//    private static final String url = "jdbc:mysql://avnadmin:AVNS_RT3DRdiLVQwdYhqDD6s@mysql-8421f11-aleksey-af53.g.aivencloud.com:14859/defaultdb?ssl-mode=REQUIRED";
//    private Connection conn;
//
//    public static void createTables() throws SQLException {
//
//        Connection conn = DriverManager.getConnection(url, "avnadmin", "AVNS_RT3DRdiLVQwdYhqDD6s");
//        Statement statement = conn.createStatement();
//        //CREATE DATABASE name;
//        String createUsers = "CREATE TABLE IF NOT EXISTS users (" +
//                "userID INT AUTO_INCREMENT PRIMARY KEY," +
//                "name VARCHAR(100) NOT NULL," +
//                "lastName VARCHAR(100) NOT NULL," +
//                "email VARCHAR(255) NOT NULL UNIQUE," +
//                "password VARCHAR(255) NOT NULL" +
//                ");";
//
//        String createCourse = "CREATE TABLE IF NOT EXISTS course (" +
//                "courseID INT AUTO_INCREMENT PRIMARY KEY," +
//                "name VARCHAR(100) NOT NULL," +
//                "userID INT," +
//                "FOREIGN KEY (userID) REFERENCES users(userID) ON DELETE CASCADE" +
//                ");";
//
//        String createAssignments = "CREATE TABLE IF NOT EXISTS assignments (" +
//                "assignmentID INT AUTO_INCREMENT PRIMARY KEY," +
//                "name VARCHAR(100) NOT NULL," +
//                "dueDate DATE NOT NULL," +
//                "status BOOLEAN," +
//                "courseID INT," +
//                "FOREIGN KEY (courseID) REFERENCES course(courseID) ON DELETE CASCADE" +
//                ");";
//
//        String createToDoList = "CREATE TABLE IF NOT EXISTS ToDoList (" +
//                "listID INT AUTO_INCREMENT PRIMARY KEY," +
//                "name VARCHAR(100) NOT NULL," +
//                "content TEXT," +
//                "status BOOLEAN," +
//                "due_date DATE," +
//                "userID INT," +
//                "FOREIGN KEY (userID) REFERENCES users(userID) ON DELETE CASCADE" +
//                ");";
//
//
//        statement.execute(createUsers);
//        statement.execute(createCourse);
//        statement.execute(createAssignments);
//        statement.execute(createToDoList);
//
//    }
//
//
//    public void addUser(String name, String lastName, String email, String password) throws SQLException {
//        String addUser = "INSERT INTO users(name, lastName, email, password) VALUES(?,?,?,?);";
//        conn = DriverManager.getConnection(url);
//        PreparedStatement statement = conn.prepareStatement(addUser);
//
//        statement.setString(1, name);
//        statement.setString(2, lastName);
//        statement.setString(3, email);
//        statement.setString(4, password);
//
//        statement.executeUpdate();
//    }
//
//    public void addToDo(String name, String content, boolean status, Date due_date){
//
//    }
//
//    public void readUser(String email) throws SQLException {
//        String readUser = "SELECT * FROM users WHERE email = ?;";
//        conn = DriverManager.getConnection(url);
//        PreparedStatement statement = conn.prepareStatement(readUser);
//        statement.setString(1, email);
//        ResultSet resultSet = statement.executeQuery();
//        while (resultSet.next()) {
//            System.out.println(resultSet.getString("name") + " " + resultSet.getString("lastName") + " " + resultSet.getString("email"));
//        }
//
//    }
//
//    public void readCourse(String courseID) throws SQLException {
//        String readCourse = "SELECT * FROM course WHERE courseID = ?;";
//        conn = DriverManager.getConnection(url);
//        PreparedStatement statement = conn.prepareStatement(readCourse);
//        statement.setString(1, courseID);
//        ResultSet resultSet = statement.executeQuery();
//        while (resultSet.next()) {
//            System.out.println(resultSet.getString("name") + " " + resultSet.getString("courseID"));
//        }
//    }
//
//    public void readAssignment(String assignmentID) throws SQLException {
//        String readAssignment = "SELECT * FROM assignments WHERE assignmentID = ?;";
//        conn = DriverManager.getConnection(url);
//        PreparedStatement statement = conn.prepareStatement(readAssignment);
//        statement.setString(1, assignmentID);
//        ResultSet resultSet = statement.executeQuery();
//        while (resultSet.next()) {
//            System.out.println(resultSet.getString("name") + " " + resultSet.getString("courseID"));
//        }
//    }
//
//    public void readToDo(String listID) throws SQLException {
//        String readToDo = "SELECT * from ToDoList where listID = ?;";
//        conn = DriverManager.getConnection(url);
//        PreparedStatement statement = conn.prepareStatement(readToDo);
//        statement.setString(1, listID);
//        ResultSet resultSet = statement.executeQuery();
//        while (resultSet.next()) {
//            System.out.println(resultSet.getString("name") + " " + resultSet.getString("courseID"));
//        }
//    }
//
//
//}
