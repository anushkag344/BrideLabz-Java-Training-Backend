package com.greet.servlet;

import com.greet.model.Greeting;
import com.greet.model.User;
import com.greet.service.GreetingService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 10 * 1024 * 1024
)
public class GreetingServlet extends HttpServlet {

    private GreetingService greetingService;

    @Override
    public void init() throws ServletException {

        WebApplicationContext context =
                WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());

        greetingService = context.getBean(GreetingService.class);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) {

            List<Greeting> greetings = greetingService.getAllGreetings();

            request.setAttribute("greetings", greetings);

            request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp")
                    .forward(request, response);

            return;
        }

        switch (action) {
            case "new":
                request.getRequestDispatcher("/WEB-INF/views/greeting-form.jsp")
                        .forward(request, response);

                break;

            case "edit":

                int id = Integer.parseInt(request.getParameter("id"));

                Greeting greeting = greetingService.getGreetingById(id);

                request.setAttribute("greeting", greeting);

                request.getRequestDispatcher("/WEB-INF/views/greeting-form.jsp")
                        .forward(request, response);

                break;

            case "delete":

                int deleteId = Integer.parseInt(request.getParameter("id"));

                Greeting existing = greetingService.getGreetingById(deleteId);

                if (existing != null &&
                        existing.getImagePath() != null &&
                        !existing.getImagePath().isEmpty()) {

                    deleteFile(existing.getImagePath());
                }

                greetingService.deleteGreeting(deleteId);

//                response.sendRedirect("greeting");
                response.sendRedirect(request.getContextPath() + "/greetings");

                break;

            default:

//                response.sendRedirect("greeting");
                response.sendRedirect(request.getContextPath() + "/greetings");

                break;
        }

    }
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        User currentUser = (User) session.getAttribute("currentUser");

        String action = request.getParameter("action");

        if ("create".equals(action)) {

            String message = request.getParameter("message");

            Part imagePart = request.getPart("image");

            String imagePath = saveUploadedFile(imagePart);

            Greeting newGreeting = new Greeting();

            newGreeting.setMessage(message);
            newGreeting.setImagePath(imagePath);
            newGreeting.setCreatedById(currentUser.getId());

//            greetingService.createGreeting(newGreeting);
//            response.sendRedirect("greeting");
            greetingService.createGreeting(newGreeting);

            response.sendRedirect(request.getContextPath() + "/greetings");

        } else if ("update".equals(action)) {

            int id = Integer.parseInt(request.getParameter("id"));

            String message = request.getParameter("message");

            Greeting existing =
                    greetingService.getGreetingById(id);

            String imagePath = existing.getImagePath();

            Part imagePart = request.getPart("image");

            if (imagePart != null &&
                    imagePart.getSize() > 0) {

                if (imagePath != null &&
                        !imagePath.isEmpty()) {

                    deleteFile(imagePath);
                }

                imagePath = saveUploadedFile(imagePart);
            }

            Greeting updatedGreeting = new Greeting();

            updatedGreeting.setId(id);
            updatedGreeting.setMessage(message);
            updatedGreeting.setImagePath(imagePath);
            updatedGreeting.setCreatedById(existing.getCreatedById());

//            greetingService.updateGreeting(updatedGreeting);
//            response.sendRedirect("greeting");

            greetingService.updateGreeting(updatedGreeting);
            response.sendRedirect(request.getContextPath() + "/greetings");
        }
    }
    private String saveUploadedFile(Part part)
            throws IOException {

        if (part == null || part.getSize() == 0) {
            return null;
        }

        String uploadPath = getServletContext().getRealPath("") +
                File.separator + "uploads";

        File uploadDir = new File(uploadPath);

        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String fileName =
                Paths.get(part.getSubmittedFileName())
                        .getFileName()
                        .toString();

        String savedFileName =
                System.currentTimeMillis() + "_" + fileName;

        String filePath =
                uploadPath + File.separator + savedFileName;

        part.write(filePath);

        return "uploads/" + savedFileName;
    }

    private void deleteFile(String relativePath) {

        if (relativePath == null || relativePath.isEmpty()) {
            return;
        }

        String absolutePath =
                getServletContext().getRealPath("") +
                        File.separator +
                        relativePath.replace("/", File.separator);

        File file = new File(absolutePath);

        if (file.exists()) {
            file.delete();
        }
    }

}