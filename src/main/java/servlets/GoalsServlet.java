package servlets;

import dao.GoalsDao;
import dao.UsersTasksDao;
import model.Goal;
import model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/goals")
public class GoalsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private GoalsDao dao;
    private UsersTasksDao tasksDao;
    private static final Logger logger = LoggerFactory.getLogger(
            GoalsServlet.class);

    public GoalsServlet() {
        super();
        dao = new GoalsDao();
        tasksDao = new UsersTasksDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int user_id = (int) session.getAttribute("user_id");
        if (request.getParameter("guid") != null){
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("updateGoal.jsp");
            requestDispatcher.forward(request, response);
        }else if (request.getParameter("del") != null){
            doDelete(request, response);
        }else if (request.getParameter("gid") != null){
            int goalId = Integer.parseInt(request.getParameter("gid"));
            List<Goal> goals = dao.getAllChildGoals(user_id, goalId);
            request.setAttribute("goals", goals);
            List<Task> tasks = tasksDao.getAllChildTasks(user_id, goalId);
            request.setAttribute("tasks", tasks);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("userGoals.jsp");
            requestDispatcher.forward(request, response);
        }else if (request.getParameter("addgoal") != null){
            List<Goal> goals = dao.getAllGoals(user_id);
            request.setAttribute("goals", goals);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("addGoal.jsp");
            requestDispatcher.forward(request, response);
        }else {
            response.setContentType("text/html");
            PrintWriter printWriter = response.getWriter();
            try{
                int id = Integer.parseInt(request.getParameter("id"));
                Goal goal = dao.getGoalByIdP(id);
                printWriter.println(goal.toString());
            }catch (Exception e){
                List<Goal> goals = dao.getAllChildGoals(user_id, 0);
                request.setAttribute("goals", goals);
                List<Task> tasks = tasksDao.getAllChildTasks(user_id, 0);
                request.setAttribute("tasks", tasks);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("userGoals.jsp");
                requestDispatcher.forward(request, response);
            }
            printWriter.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("guid") != null){
            doPut(request, response);
        }else {
            HttpSession session = request.getSession();
            Goal goal = new Goal();
            goal.setText(request.getParameter("text"));
            if (request.getParameter("parentgoal") != null)
            goal.setParentGoal(Integer.parseInt(request.getParameter("parentgoal")));
            int user_id = (int) session.getAttribute("user_id");
            logger.debug("Try to add new goal: {}", goal.toString());
            dao.addGoal(goal, user_id);
            doGet(request,response);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("del"));
        Goal goal = dao.getGoalByIdP(id);
        logger.debug("Try to delete new goal: {}", goal.toString());
        dao.deleteGoal(goal);
        response.sendRedirect("/goals");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("guid"));
        Goal goal = dao.getGoalByIdP(id);
        String text = request.getParameter("text");
        if (text != null)
            goal.setText(text);
        else logger.info("Try to update goal: {}. Text didn't find in params", goal.toString());
        logger.info("Try to update goal: {}.", goal.toString());
        dao.updateGoal(goal);
        response.sendRedirect("/goals");
    }
}
