package util;

import dao.UserDao;
import model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgreSQLJDBC {

    private static String url = "jdbc:postgresql://localhost:5432/epamTrainingWeb";
    private static Connection connection = null;
    public static void closeConnection(){
        try {
            connection.close();
            System.out.println("Connection to db was closed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        if (connection != null)
            return connection;
        else {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager
                        .getConnection(url,
                                "postgres", "qwerty");
                System.out.println("Opened database successfully");
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                System.err.println(e.getClass().getName()+": "+e.getMessage());
            }
            return connection;
        }
    }

    public static void createNewUserTable() {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF not EXISTS USERS (" +
                "id SERIAL PRIMARY KEY," +
                "name TEXT," +
                "password TEXT," +
                "email TEXT)";
        try  {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            System.out.println("User table created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createNewTaskTable() {
        String sql = "CREATE TABLE IF not EXISTS TASKS (" +
                "id SERIAL PRIMARY KEY," +
                "text TEXT," +
                "description TEXT," +
                "deadline TEXT," +
                "status TEXT)";
        try  {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            System.out.println("Tasks table created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createUsersTasksTable() {
        String sql = "CREATE TABLE IF not EXISTS USERSTASKS (" +
                "id SERIAL PRIMARY KEY," +
                "user_id integer REFERENCES USERS (id)," +
                "goal_id integer," +
                "text TEXT," +
                "description TEXT," +
                "deadline TEXT," +
                "status TEXT)";
        try  {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            System.out.println("USERSTASKS Table created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createUsersGoalsTable() {
        String sql = "CREATE TABLE IF not EXISTS USERSGOALS (" +
                "id SERIAL PRIMARY KEY," +
                "user_id integer REFERENCES USERS (id)," +
                "text TEXT," +
                "parentgoal integer)";
        try  {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            System.out.println("USERSGOALS Table created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void closeQuietly(Connection conn) {
        try {
            conn.close();
        } catch (Exception e) {
        }
    }

    public static void rollbackQuietly(Connection conn) {
        try {
            conn.rollback();
        } catch (Exception e) {
        }
    }
}
