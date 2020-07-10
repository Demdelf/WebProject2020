package dao;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.PostgreSQLJDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private Connection connection;
    private static final Logger logger = LoggerFactory.getLogger(
            UserDao.class);

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
            logger.trace("User {} has been registered", user.toString());
        } catch (SQLException e) {
            logger.error("User: {} can't be reg: {}", user.toString(), e.getMessage());
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
            logger.trace("User {} has been added", user.toString());
        } catch (SQLException e) {
            logger.error("User: {} can't be add: {}", user.toString(), e.getMessage());
        }
    }

    public void deleteUser(int userId) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM users WHERE userid=?");
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
            logger.trace("User id: {} has been added", userId);
        } catch (SQLException e) {
            logger.error("User id: {} can't be deleted: {}", userId, e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from users");
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setName(rs.getString("name"));
                users.add(user);
            }
        } catch (SQLException e) {
            logger.error("Can't get all users: {}", e.getMessage());
        }
        return users;
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
            logger.error("Can't get user by name {}: {}", name, e.getMessage());
        }
        return user;
    }

    public boolean haveUserWithName(String name){
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * from users where name=?");
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            logger.error("Can't check have user by name {}: {}", name, e.getMessage());
        }
        return false;
    }
}
