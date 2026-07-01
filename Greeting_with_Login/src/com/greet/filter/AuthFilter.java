package com.greet.filter;

import com.greet.model.User;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
    public class AuthFilter implements Filter {
        @Override
        public void init(FilterConfig filterConfig) throws ServletException {}
        @Override
        public void doFilter(ServletRequest request, ServletResponse response,
                             FilterChain chain)
                throws IOException, ServletException {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            HttpSession session = httpRequest.getSession(false);
            String path =
                    httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
            // 1. Exclude public assets and controllers
            if (path.equals("/login") || path.equals("/register") ||
                    path.equals("/logout") ||
                    path.equals("/unauthorized") || path.startsWith("/uploads/") ||
                    path.equals("/index.jsp")) {
                chain.doFilter(request, response);
                return;
            }
            // 2. Active Session Authentication check
            if (session == null || session.getAttribute("currentUser") == null) {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
                return;
            }
            User user = (User) session.getAttribute("currentUser");
            // 3. Authorization Role Check
            // Only ADMIN can Create, Edit, or Delete greetings
            if (path.equals("/greetings/new") || path.equals("/greetings/edit") ||
                    path.equals("/greetings/delete")) {
                if (!"ADMIN".equals(user.getRole())) {
                    httpResponse.sendRedirect(httpRequest.getContextPath() +
                            "/unauthorized");
                    return;
                }
            }
    chain.doFilter(request, response);
    }
    @Override
    public void destroy() {}
}
