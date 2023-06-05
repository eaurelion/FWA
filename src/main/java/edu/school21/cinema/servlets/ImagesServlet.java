package edu.school21.cinema.servlets;
import edu.school21.cinema.models.User;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

@WebServlet("/images/*")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class ImagesServlet extends HttpServlet {
    private ApplicationContext springContext;
    @Autowired
    private String uploadPath;

    @Override
    public void init(ServletConfig config) {
        springContext = (ApplicationContext) config.getServletContext().getAttribute("springContext");
        uploadPath = springContext.getBean("uploadPath", String.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        User user = (User) request.getSession().getAttribute("user");
        String[] URIParts = request.getRequestURI().split("/");
        String URI = URIParts[URIParts.length - 1];

        if (user == null)
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        else
            try {
                byte[] fileContent = FileUtils.readFileToByteArray(new File(uploadPath + user.getId() + File.separator + URI));
                String encodedString = Base64.getEncoder().encodeToString(fileContent);
                request.setAttribute("image", encodedString);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/image.jsp");
                dispatcher.forward(request, response);
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        File dir = new File(uploadPath + user.getId());

        if (!dir.exists())
            dir.mkdir();
        try {
            for (Part part : request.getParts()) {
                part.write(dir + File.separator + part.getSubmittedFileName());
            }
        } catch (Exception e) {}
        response.sendRedirect("profile");
    }
}
