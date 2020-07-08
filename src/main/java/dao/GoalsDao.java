package dao;

import model.Goal;
import model.Status;
import model.Task;
import util.PostgreSQLJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GoalsDao {
    private Connection connection;
    private final String tableName = "USERSGOALS";

    public GoalsDao() {
        connection = PostgreSQLJDBC.getConnection();
        PostgreSQLJDBC.createUsersGoalsTable();
    }

    public void addGoal(Goal goal, int user_id) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO " + tableName + "(user_id, text, parentgoal) VALUES (?, ?, ?)");
            // Parameters start with 1
            preparedStatement.setInt(1, user_id);
            preparedStatement.setString(2, goal.getText());
            preparedStatement.setInt(3, goal.getParentGoal());

            preparedStatement.executeUpdate();
            System.out.println("Goal has been added");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateGoal(Goal goal) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE " + tableName + " SET text =? where id=?");

            preparedStatement.setString(1, goal.getText());
            preparedStatement.setInt(2, goal.getId());
            preparedStatement.executeUpdate();
            System.out.println("Goal has been updated");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteGoal(Goal goal) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM " + tableName + " where id=?");
            preparedStatement.setInt(1, goal.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM " + tableName + " where parentgoal=?");
            preparedStatement.setInt(1, goal.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM " + "USERSTASK" + " where goal_id=?");
            preparedStatement.setInt(1, goal.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Goal> getAllGoals(int user_id) {
        List<Goal> goals = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + tableName + " where user_id=?");
            ps.setInt(1, user_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Goal goal = new Goal();
                goal.setId(rs.getInt("id"));
                goal.setText(rs.getString("text"));
                goal.setParentGoal(rs.getInt("parentgoal"));
                goals.add(goal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return goals;
    }
    public List<Goal> getAllChildGoals(int user_id, int parent_id) {
        List<Goal> goals = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + tableName +
                    " where user_id=? AND parentgoal=?");
            ps.setInt(1, user_id);
            ps.setInt(2, parent_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Goal goal = new Goal();
                goal.setId(rs.getInt("id"));
                goal.setText(rs.getString("text"));
                goal.setParentGoal(rs.getInt("parentgoal"));
                goals.add(goal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return goals;
    }

    public Goal getGoalByIdP(int goalId) {
        Goal goal = new Goal();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + tableName + " where id=?;");
            ps.setInt(1, goalId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                goal.setId(rs.getInt("id"));
                goal.setText(rs.getString("text"));
                goal.setParentGoal(rs.getInt("parentgoal"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return goal;
    }
}
