package com.akademik.sisko.config;

import com.akademik.sisko.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect("/login?error=unauthorized");
            return false;
        }

        User user = (User) session.getAttribute("currentUser");
        String uri = request.getRequestURI();

        if (uri.startsWith("/admin") && !"ADMIN".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect("/login?error=forbidden");
            return false;
        }

        if (uri.startsWith("/guru") && !"GURU".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect("/login?error=forbidden");
            return false;
        }

        return true;
    }
}
