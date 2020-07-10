package servlets;

import dao.GoalsDao;
import dao.UsersTasksDao;
import model.Goal;
import model.Status;
import model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.PostgreSQLJDBC;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet("/userstasks")
public class UsersTasksServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UsersTasksDao dao;
    private GoalsDao goalsDao;
    private static final Logger logger = LoggerFactory.getLogger(
            UsersTasksServlet.class);

    public UsersTasksServlet() {
        super();
        dao = new UsersTasksDao();
        goalsDao = new GoalsDao();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        int user_id = (int) session.getAttribute("user_id");
        if (request.getParameter("tid") != null){
            List<Status> listStatus = new ArrayList<>( Arrays.asList(Status.values() ));
            request.setAttribute("listStatus", listStatus);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("updateTask.jsp");
            requestDispatcher.forward(request, response);
        }else if (request.getParameter("del") != null){
            doDelete(request, response);
        }else if (request.getParameter("addtask") != null){
            List<Goal> goals = goalsDao.getAllGoals(user_id);
            request.setAttribute("goals", goals);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("addTask.jsp");
            requestDispatcher.forward(request, response);
        }else {
            response.setContentType("text/html");
            PrintWriter printWriter = response.getWriter();
            List<Status> listStatus = new ArrayList<>( Arrays.asList(Status.values() ));
            request.setAttribute("listStatus", listStatus);
            try{
                int id = Integer.parseInt(request.getParameter("id"));
                Task task = dao.getTaskByIdP(id);
                printWriter.println(task.toString());
            }catch (Exception e){
                List<Task> tasks = dao.getAllTasks(user_id);
                request.setAttribute("tasks", tasks);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("userstasks.jsp");
                requestDispatcher.forward(request, response);
            }
            printWriter.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PostgreSQLJDBC.createUsersTasksTable();
        if (request.getParameter("tid") != null){
            doPut(request, response);
        }else {
            HttpSession session = request.getSession();
            Task task = new Task();
            task.setText(request.getParameter("text"));
            task.setDescription(request.getParameter("description"));
            try{
                LocalDate date = LocalDate.parse(request.getParameter("deadline"));
                task.setTimeToBeCompleted(date);
            }catch (Exception e){
                logger.info("New Task hasn't DeadLine");
            }
            try{
                Status status = Status.valueOf(request.getParameter("status"));
                task.setStatus(status);
            }catch (Exception e){
                logger.debug("New Task hasn't Status, default NEW");
            }
            task.setParentGoalId(Integer.parseInt(request.getParameter("parentgoal")));
            int user_id = (int) session.getAttribute("user_id");
            logger.debug("New Task: {}", task.toString());
            dao.addTask(task, user_id);
            response.sendRedirect("/goals");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("del"));
        Task task = dao.getTaskByIdP(id);
        logger.debug("Task for deleting: {}", task.toString());
        dao.deleteTaskP(task);
        response.sendRedirect("/goals");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("tid"));
        Task task = dao.getTaskByIdP(id);
        try{
            String text = request.getParameter("text");
            if (text != null)
                task.setText(text);
        }catch (Exception e){
            logger.info("Update Task with id [{}]: Text didn't find in params", id);
        }
        try{
            String description = request.getParameter("description");
            if (description != null)
                task.setDescription(description);
        }catch (Exception e){
            logger.info("Update Task with id [{}]: Description didn't find in params", id);
        }
        try{
            LocalDate date = LocalDate.parse(request.getParameter("deadline"));
            task.setTimeToBeCompleted(date);

        }catch (Exception e){
            logger.info("Update Task with id [{}]: Deadline didn't find in params", id);
        }
        try{
            Status status = Status.valueOf(request.getParameter("status"));
            task.setStatus(status);
        }catch (Exception e){
            logger.info("Update Task with id [{}]: State didn't find in params", id);
        }
        logger.debug("Update Task with id [{}]: {}", id, task.toString());
        dao.updateTaskP(task);
        response.sendRedirect("/goals");
    }
}
