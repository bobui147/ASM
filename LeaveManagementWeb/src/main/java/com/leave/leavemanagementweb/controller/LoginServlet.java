package com.leave.leavemanagementweb.controller;

import com.leave.leavemanagementweb.dao.UserDAO;
import com.leave.leavemanagementweb.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = UserDAO.checkLogin(username, password);
        if (user != null) {
            req.getSession().setAttribute("user", user);
            resp.sendRedirect("dashboard.jsp");
        } else {
            req.setAttribute("error", "Sai tài khoản hoặc mật khẩu!");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}
