package com.example.main;

import com.example.main.modals.User;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;

@Component
public class MySqlDatabase {

    private static String host = "mysql-8421f11-aleksey-af53.g.aivencloud.com";
    private static String port = "14859";
    private static String databaseName = "defaultdb";
    private static String userName = "avnadmin";
    private static String password = "AVNS_RT3DRdiLVQwdYhqDD6s";


    public static void main(String[] args) throws ClassNotFoundException {


        for (int i = 0; i < args.length - 1; i++) {
            switch (args[i].toLowerCase(Locale.ROOT)) {
                case "-host": host = args[++i]; break;
                case "-username": userName = args[++i]; break;
                case "-password": password = args[++i]; break;
                case "-database": databaseName = args[++i]; break;
                case "-port": port = args[++i]; break;
            }
        }

        if (host == null || port == null || databaseName == null) {
            System.out.println("Host, port, database information is required");
            return;
        }
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (final Connection connection =
                     DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + databaseName + "?sslmode=require", userName, password);
             final Statement statement = connection.createStatement();
             final ResultSet resultSet = statement.executeQuery("SELECT version() AS version")) {

            while (resultSet.next()) {
                System.out.println("Version: "+ resultSet.getString("version"));
            }
        } catch (SQLException e) {
            System.out.println("Connection error: "+ e);
        }
    }

    public static void createTables() throws SQLException {

        final Connection connection =
                DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + databaseName + "?sslmode=require", userName, password);
        final Statement statement = connection.createStatement();

        String createUsers = "CREATE TABLE IF NOT EXISTS users (" +
                "userID INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(100) NOT NULL," +
                "last_name VARCHAR(100) NOT NULL," +
                "email VARCHAR(255) NOT NULL UNIQUE," +
                "password VARCHAR(255) NOT NULL" +
                ");";

        String createCourse = "CREATE TABLE IF NOT EXISTS course (" +
                "courseID INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(100) NOT NULL," +
                "userID INT," +
                "FOREIGN KEY (userID) REFERENCES users(userID) ON DELETE CASCADE" +
                ");";

        String createAssignments = "CREATE TABLE IF NOT EXISTS assignments (" +
                "assignmentID INT NOT NULL  AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(100) NOT NULL," +
                "dueDate DATE NOT NULL," +
                "status BOOLEAN," +
                "courseID INT," +
                "FOREIGN KEY (courseID) REFERENCES course(courseID) ON DELETE CASCADE" +
                ");";

        String createToDoList = "CREATE TABLE IF NOT EXISTS ToDoList (" +
                "listID INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(100) NOT NULL," +
                "content TEXT," +
                "status BOOLEAN," +
                "due_date DATE," +
                "userID INT," +
                "FOREIGN KEY (userID) REFERENCES users(userID) ON DELETE CASCADE" +
                ");";


        statement.execute(createUsers);
        statement.execute(createCourse);
        statement.execute(createAssignments);
        statement.execute(createToDoList);

    }
    public static User getByID(int ID) throws SQLException {
        final Connection connection =
                DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + databaseName + "?sslmode=require", userName, password);
        final Statement statement = connection.createStatement();
        String getUser = "SELECT * FROM users WHERE userID = " + ID + ";";
        ResultSet result = statement.executeQuery(getUser);
        User foundUser = new User();
        if(!result.next()){
            return null;
        }
        //probably not a good idea to give away the password, but other internal processes might need it for validation.
        while(result.next()){
            foundUser.setUserID(result.getInt("userID"));
            foundUser.setEmail(result.getString("password"));
            foundUser.setName(result.getString("name"));
            foundUser.setPassword(result.getString("password"));
            foundUser.setLast_name(result.getString("last_name"));
        }
        return foundUser;
    }
}