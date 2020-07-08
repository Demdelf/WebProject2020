package servlets;

import dao.UserDao;
import model.User;
import util.PasswordGenerator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@WebServlet("/reg")
public class RegServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("login");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        UserDao userDao = new UserDao();
        if (userDao.getUserByName(userName) != null){
            System.out.println("User with this name already exist!");
            response.sendRedirect("/register.jsp");
        }
        else {
            try {
                User user = new User();
                user.setName(userName);
                user.setEmail(email);
                user.setPassword(PasswordGenerator.generateStorngPasswordHash(password));
                userDao.addUserP(user);
                response.sendRedirect("/login.jsp");
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                e.printStackTrace();
            }
        }
    }
}
