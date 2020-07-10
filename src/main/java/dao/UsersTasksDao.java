package dao;

import model.Status;
import model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlets.RegServlet;
import util.PostgreSQLJDBC;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UsersTasksDao {
    private static final Logger logger = LoggerFactory.getLogger(
            UsersTasksDao.class);
    private Connection connection;

    public UsersTasksDao() {
        connection = PostgreSQLJDBC.getConnection();
        PostgreSQLJDBC.createUsersTasksTable();
    }

    public void addTask(Task task, int user_id) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO USERSTASKS(user_id, goal_id, text, description, deadline, status) " +
                            "VALUES (?, ?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, task.getParentGoalId());
            preparedStatement.setString(3, task.getText());
            preparedStatement.setString(4, task.getDescription());
            if (task.getTimeToBeCompleted() == null){
                preparedStatement.setString(5, null);
            }else {
                preparedStatement.setString(5, task.getTimeToBeCompleted().toString());
            }
            if (task.getStatus() == null){
                preparedStatement.setString(6, "NEW");
            }else {
                preparedStatement.setString(6, String.valueOf(task.getStatus()));
            }
            preparedStatement.executeUpdate();
            logger.trace("Task name: {} has been added", task.getText());
        } catch (SQLException e) {
            logger.error("Task name: {} can't be added: {}", task.getText(), e.getMessage());
        }
    }

    public void updateTaskP(Task task) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE USERSTASKS SET text =?, description=?, deadline=?, status=? where id=?");
            preparedStatement.setString(1, task.getText());
            preparedStatement.setString(2, task.getDescription());
            if (task.getTimeToBeCompleted() == null){
                preparedStatement.setString(3, null);
            }else {
                preparedStatement.setString(3, task.getTimeToBeCompleted().toString());
            }
            if (task.getStatus() == null){
                preparedStatement.setString(4, "NEW");
            }else {
                preparedStatement.setString(4, String.valueOf(task.getStatus()));
            }
            preparedStatement.setInt(5, task.getId());
            preparedStatement.executeUpdate();
            logger.trace("Task name: {} has been updated", task.getText());
        } catch (SQLException e) {
            logger.error("Task name: {} can't be updated: {}", task.getText(), e.getMessage());
        }
    }

    public void deleteTaskP(Task task) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM USERSTASKS where id=?");
            preparedStatement.setInt(1, task.getId());
            preparedStatement.executeUpdate();
            logger.trace("Task name: {} has been deleted", task.getText());
        } catch (SQLException e) {
            logger.error("Task name: {} can't be deleted: {}", task.getText(), e.getMessage());
        }
    }

    public List<Task> getAllTasks(int user_id) {
        List<Task> tasks = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM USERSTASKS where user_id=?;");
            ps.setInt(1, user_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Task task = new Task();
                task.setId(rs.getInt("id"));
                task.setText(rs.getString("text"));
                task.setDescription(rs.getString("description"));
                try{
                    LocalDate date = LocalDate.parse(rs.getString("deadline"));
                    task.setTimeToBeCompleted(date);
                }catch (Exception e){
                    e.printStackTrace();
                }
                try{
                    Status status = Status.valueOf(rs.getString("status"));
                    task.setStatus(status);
                }catch (Exception e){
                    e.printStackTrace();
                }
                tasks.add(task);
            }
        } catch (SQLException e) {
            logger.error("For user id: {} can't get all tasks: {}", user_id, e.getMessage());
        }
        return tasks;
    }

    public Task getTaskByIdP(int taskId) {
        Task task = new Task();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM USERSTASKS where id=?;");
            ps.setInt(1, taskId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                task.setId(rs.getInt("id"));
                task.setText(rs.getString("text"));
                task.setDescription(rs.getString("description"));
                try{
                    LocalDate date = LocalDate.parse(rs.getString("deadline"));
                    task.setTimeToBeCompleted(date);
                }catch (Exception e){
                    logger.trace("Task id: {} hasn't deadline", taskId);
                }
                try{
                    Status status = Status.valueOf(rs.getString("status"));
                    task.setStatus(status);
                }catch (Exception e){
                    logger.trace("Task id: {} hasn't status", taskId);
                }
            }
        } catch (SQLException e) {
            logger.error("For task id: {} can't find this task: {}", taskId, e.getMessage());
        }
        return task;
    }

    public List<Task> getAllChildTasks(int user_id, int goalId) {
        List<Task> tasks = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM USERSTASKS " +
                    "where user_id=? and goal_id=?;");
            ps.setInt(1, user_id);
            ps.setInt(2, goalId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Task task = new Task();
                task.setId(rs.getInt("id"));
                task.setText(rs.getString("text"));
                task.setDescription(rs.getString("description"));
                try{
                    LocalDate date = LocalDate.parse(rs.getString("deadline"));
                    task.setTimeToBeCompleted(date);
                }catch (Exception e){
                    logger.trace("Task id: {} hasn't deadline", task.getId());
                }
                try{
                    Status status = Status.valueOf(rs.getString("status"));
                    task.setStatus(status);
                }catch (Exception e){
                    logger.trace("Task id: {} hasn't status", task.getId());
                }
                task.setParentGoalId(Integer.parseInt(rs.getString("goal_id")));
                tasks.add(task);
            }
        } catch (SQLException e) {
            logger.error("For goal id: {} can't find child tasks: {}", goalId, e.getMessage());
        }
        return tasks;
    }

}
