package com.leave.leavemanagementweb.controller;

import com.leave.leavemanagementweb.dao.UserDAO;
import com.leave.leavemanagementweb.model.User;
import com.leave.leavemanagementweb.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = UserDAO.checkLogin(username, password);
        if (user != null) {

            // ✅ Truy vấn role sau khi login
            try (Connection conn = DBConnection.getConnection()) {
                String sql = """
                    SELECT r.role_name
                    FROM User_Roles ur
                    JOIN Roles r ON ur.role_id = r.role_id
                    WHERE ur.user_id = ?
                """;
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, user.getUserId());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    user.setRole(rs.getString("role_name"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            req.getSession().setAttribute("user", user);
            resp.sendRedirect("dashboard.jsp");
        } else {
            req.setAttribute("error", "Sai tài khoản hoặc mật khẩu!");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}
