package edu.school21.cinema.servlets;

import edu.school21.cinema.services.UserService;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet {
    private ApplicationContext springContext;

    @Override
    public void init(ServletConfig config) {
        springContext = (ApplicationContext) config.getServletContext().getAttribute("springContext");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/signUp.jsp");
        dispatcher.forward(request, response);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        UserService userService = springContext.getBean("userService", UserService.class);

        if (userService.signUp(request.getParameter("login"), request.getParameter("password"), request.getRemoteAddr())) {
            HttpSession session = request.getSession();
            session.setAttribute("user", userService.getProfile(request.getParameter("login")));
            session.setAttribute("auth", userService.getAuth(request.getParameter("login")));
            response.sendRedirect("profile");
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
