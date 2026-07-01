package com.greet.servlet;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.greet.model.Greeting;
import com.greet.service.GreetingService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Front controller servlet that handles greeting requests.
 *
 * Flow:
 * 1. init() -> Loads Spring ApplicationContext from applicationContext.xml
 * 2. doGet() -> Forwards to index.jsp
 * 3. doPost() -> Calls GreetingService and forwards to greeting.jsp
 *
 * Mapped to "/greet" in web.xml.
 */
public class GreetingServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /** Spring Application Context */
    private ApplicationContext context;

    /** Greeting Service Bean */
    private GreetingService greetingService;

    /**
     * Initializes the Spring ApplicationContext.
     */
    @Override
    public void init() throws ServletException {
        super.init();

        System.out.println("=== GreetingServlet.init() - Loading Spring Context ===");

        // Get the absolute path of applicationContext.xml
        String xmlPath = getServletContext()
                .getRealPath("/WEB-INF/applicationContext.xml");

        // Load Spring context
        context = new ClassPathXmlApplicationContext("file:" + xmlPath);

        // Get GreetingService bean
        greetingService = context.getBean("greetingService", GreetingService.class);

        System.out.println("=== Spring Context loaded successfully ===");
    }

    /**
     * Handles GET requests.
     * Displays the input form.
     */
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/index.jsp")
                .forward(request, response);
    }

    /**
     * Handles POST requests.
     * Generates the greeting and forwards to greeting.jsp.
     */
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        // Read form parameter
        String userName = request.getParameter("userName");

        // Generate greeting
        Greeting greeting = greetingService.greet(userName);

        // Pass object to JSP
        request.setAttribute("greeting", greeting);

        // Forward to result page
        request.getRequestDispatcher("/greeting.jsp")
                .forward(request, response);
    }
}