package servlets;

import dao.UserDao;
import model.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/user")
public class UserControllerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDao dao;

    public UserControllerServlet() {
        super();
        dao = new UserDao();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User();
        user.setName(request.getParameter("name"));
        dao.regUser(user);
        request.setAttribute("users", dao.getAllUsers());
    }
}
