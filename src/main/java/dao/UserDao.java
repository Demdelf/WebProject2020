package dao;

import model.User;
import util.PostgreSQLJDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDao {

    private Connection connection;

    public UserDao() {
        connection = PostgreSQLJDBC.getConnection();
        PostgreSQLJDBC.createNewUserTable();
    }

    public void regUser(User user) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO users(name, password, email) VALUES (?,?,?)");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.executeUpdate();
            System.out.println("User has been registered");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addUserP(User user) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO USERS(name, email, password) VALUES (?, ?, ?)");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());

            preparedStatement.executeUpdate();
            System.out.println("User has been added");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(int userId) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM users WHERE userid=?");
            // Parameters start with 1
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from users");
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setName(rs.getString("name"));
                //user.setLastName(rs.getString("lastname"));
                //user.setDob(rs.getDate("dob"));
                //user.setEmail(rs.getString("email"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public List<User> getAllUsersP() {
        List<User> users = new ArrayList<User>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM USERS;");
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public User getUserById(int userId) {
        User user = new User();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * from users where userid=?");
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                user.setUserId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public User getUserByName(String name) {
        User user = new User();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * from users where name=?");
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                user.setUserId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
            }else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public boolean haveUserWithName(String name){
        User user = new User();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * from users where name=?");
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
