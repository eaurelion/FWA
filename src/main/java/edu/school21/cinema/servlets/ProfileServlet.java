package edu.school21.cinema.servlets;

import edu.school21.cinema.models.User;
import edu.school21.cinema.services.UserService;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;

@WebServlet("/profile")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class ProfileServlet extends HttpServlet {
    private ApplicationContext springContext;

    @Autowired
    private String uploadPath;

    @Override
    public void init(ServletConfig config) {
        springContext = (ApplicationContext) config.getServletContext().getAttribute("springContext");
        uploadPath = springContext.getBean("uploadPath", String.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        File dir = new File(uploadPath + user.getId());
        if (!dir.exists())
            dir.mkdir();
        request.setAttribute("uploadPath", uploadPath + user.getId());
        File image = new File(uploadPath + user.getId());
        for (File file : image.listFiles())
            if (file.getName().contains("DS_Store"))
                file.delete();
        if ((image.listFiles().length != 0)) {
            File[] files = image.listFiles();
            Arrays.sort(files, (f1, f2) -> Long.valueOf(f1.lastModified()).compareTo(f2.lastModified()));
            byte[] fileContent = FileUtils.readFileToByteArray(files[files.length - 1]);
            String encodedString = Base64.getEncoder().encodeToString(fileContent);
            request.setAttribute("image", encodedString);
        }
        UserService userService = springContext.getBean("userService", UserService.class);
        session.setAttribute("auth", userService.getAuth(((User) session.getAttribute("user")).getLogin()));
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/profile.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        request.setCharacterEncoding("UTF-8");
        UserService userService = springContext.getBean("userService", UserService.class);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (request.getParameter("firstname").length() > 0)
            user.setFirstName(request.getParameter("firstname"));
        if (request.getParameter("lastname").length() > 0)
            user.setLastName(request.getParameter("lastname"));
        if (request.getParameter("phone").length() > 0)
            user.setPhoneNumber(request.getParameter("phone"));
        userService.updateProfile(user);
        response.sendRedirect("profile");
    }
}
