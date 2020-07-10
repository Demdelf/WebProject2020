package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostgreSQLJDBC {
    private static final Logger logger = LoggerFactory.getLogger(PostgreSQLJDBC.class);
    private static String url = "jdbc:postgresql://localhost:5432/epamTrainingWeb";
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection != null)
            return connection;
        else {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager
                        .getConnection(url,
                                "postgres", "qwerty");
                logger.info("Connected to db successfully");
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                System.err.println(e.getClass().getName()+": "+e.getMessage());
                logger.error("Can't connect to db");
                throw new IllegalStateException();
            }
            return connection;
        }
    }

    public static void createNewUserTable() {
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
            logger.debug("User table used or created successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            logger.warn("Can't used or create User table");
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
            logger.debug("USERSTASKS table used or created successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            logger.warn("Can't used or create USERSTASKS table");
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
            logger.debug("USERSGOALS table used or created successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            logger.warn("Can't used or create USERSGOALS table");
        }
    }
}
