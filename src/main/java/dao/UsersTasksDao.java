package dao;

import model.Status;
import model.Task;
import model.User;
import util.PostgreSQLJDBC;

import javax.servlet.http.HttpSession;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UsersTasksDao {
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
            // Parameters start with 1
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
            System.out.println("Task has been added");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTaskP(Task task) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE USERSTASKS SET text =?, description=?, deadline=?, status=? where id=?");
            // Parameters start with 1

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

            //preparedStatement.setString(3, task.getTimeToBeCompleted().toString());
            //preparedStatement.setString(4, String.valueOf(task.getStatus()));
            preparedStatement.setInt(5, task.getId());
            preparedStatement.executeUpdate();
            System.out.println("Task has been updated");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTaskP(Task task) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM USERSTASKS where id=?");
            // Parameters start with 1

            preparedStatement.setInt(1, task.getId());
            preparedStatement.executeUpdate();
            System.out.println("Task has been deleted");

        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
                //task.setTimeToBeCompleted(LocalDate.parse(rs.getString("deadline")));
                //task.setText(rs.getString("status"));
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
                    e.printStackTrace();
                }

                try{
                    Status status = Status.valueOf(rs.getString("status"));
                    task.setStatus(status);

                }catch (Exception e){
                    e.printStackTrace();
                }
                task.setParentGoalId(Integer.parseInt(rs.getString("goal_id")));

                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

/*    public Task getTaskByText(String text) {
        Task task = new Task();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * from userstasks where text=?");
            preparedStatement.setString(1, text);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                task.setUserId(rs.getInt("id"));
                task.setName(rs.getString("name"));
                task.setPassword(rs.getString("password"));
                task.setEmail(rs.getString("email"));
            }else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }*/
}
