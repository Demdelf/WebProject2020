package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@WebFilter("/*")//Все страницы сайта обрабатывает данный фильтр
public class LoginFilter implements Filter {

    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/logout", "/reg", "/register.jsp")));

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //получение данных сессии
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        //URL Запроса/переадресации на Servlet входа
        String loginURI = request.getContextPath() + "/login";
        //Если сессия ранее создана
        boolean loggedIn = session != null && session.getAttribute("name") != null ;
        boolean loginRequest = request.getRequestURI().equals(loginURI);
        //Если запрос пришел со страницы с входом или сессия не пуста даем добро следовать дальше
        //Если нет ридерект на страницу входа
        String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
        boolean allowedPath = ALLOWED_PATHS.contains(path);
        if (loggedIn || loginRequest || allowedPath) {
            filterChain.doFilter(request, response);
        } else {
            System.out.println("Необходимо войти");
            response.sendRedirect(loginURI);
        }
    }

    @Override
    public void destroy() {
    }
}
