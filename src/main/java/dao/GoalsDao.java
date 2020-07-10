package dao;

import model.Goal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.PostgreSQLJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GoalsDao {
    private Connection connection;
    private final String tableName = "USERSGOALS";
    private static final Logger logger = LoggerFactory.getLogger(
            GoalsDao.class);

    public GoalsDao() {
        connection = PostgreSQLJDBC.getConnection();
        PostgreSQLJDBC.createUsersGoalsTable();
    }

    public void addGoal(Goal goal, int user_id) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO " + tableName + "(user_id, text, parentgoal) VALUES (?, ?, ?)");
            preparedStatement.setInt(1, user_id);
            preparedStatement.setString(2, goal.getText());
            preparedStatement.setInt(3, goal.getParentGoal());
            preparedStatement.executeUpdate();
            logger.trace("Goal {} has been added", goal.toString());
        } catch (SQLException e) {
            logger.error("Goal: {} can't be add: {}", goal.toString(), e.getMessage());
        }
    }

    public void updateGoal(Goal goal) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE " + tableName + " SET text =? where id=?");
            preparedStatement.setString(1, goal.getText());
            preparedStatement.setInt(2, goal.getId());
            preparedStatement.executeUpdate();
            logger.trace("Goal {} has been updated", goal.toString());
        } catch (SQLException e) {
            logger.error("Goal: {} can't be updated: {}", goal.toString(), e.getMessage());
        }
    }

    public void deleteGoal(Goal goal) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM " + tableName + " where id=?");
            preparedStatement.setInt(1, goal.getId());
            preparedStatement.executeUpdate();
            logger.trace("Goal {} has been deleted", goal.toString());
        } catch (SQLException e) {
            logger.error("Goal: {} can't be deleted: {}", goal.toString(), e.getMessage());
        }
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM " + tableName + " where parentgoal=?");
            preparedStatement.setInt(1, goal.getId());
            preparedStatement.executeUpdate();
            logger.trace("For Goal {} child goals have been deleted", goal.toString());
        } catch (SQLException e) {
            logger.error("For Goal: {} child goals can't be deleted: {}", goal.toString(), e.getMessage());
        }
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM " + "USERSTASK" + " where goal_id=?");
            preparedStatement.setInt(1, goal.getId());
            preparedStatement.executeUpdate();
            logger.trace("For Goal {} child tasks have been deleted", goal.toString());
        } catch (SQLException e) {
            logger.error("For Goal: {} child tasks can't be deleted: {}", goal.toString(), e.getMessage());
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
            logger.error("For user id: {} can't get all goals: {}", user_id, e.getMessage());
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
            logger.error("For user id: {} and goal id {} can't get all child goals: {}"
                    , user_id, parent_id, e.getMessage());
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
            logger.error("For goal id: {} can't find this goal: {}", goalId, e.getMessage());
        }
        return goal;
    }
}
