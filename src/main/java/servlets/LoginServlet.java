package servlets;

import dao.UserDao;
import model.User;
import util.PasswordGenerator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("out") != null){
            HttpSession session = req.getSession(false);
            session.setAttribute("name", null);
        }
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("login");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        UserDao userDao = new UserDao();
        User user = userDao.getUserByName(userName);
        if (!userDao.haveUserWithName(userName)){
            System.out.println("No user with this name!");
            response.sendRedirect("/register.jsp");
        }
        else {
            try {
                if (user.getPassword().equals(PasswordGenerator.generateStorngPasswordHash(password))){
                    session.setAttribute("name",user.getName());
                    session.setAttribute("email",user.getEmail());
                    session.setAttribute("user_id", user.getUserId());
                    response.sendRedirect("/goals");
                }else {
                    System.out.println("Wrong password!");
                    response.sendRedirect("/login.jsp");
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }
        }
    }
}
