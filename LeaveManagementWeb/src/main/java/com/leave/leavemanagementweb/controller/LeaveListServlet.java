package com.leave.leavemanagementweb.controller;

import com.leave.leavemanagementweb.dao.LeaveRequestDAO;
import com.leave.leavemanagementweb.model.LeaveRequest;
import com.leave.leavemanagementweb.model.User;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

@WebServlet("/leave-list")
public class LeaveListServlet extends HttpServlet {

    private final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=LeaveManagement;encrypt=true;trustServerCertificate=true";
    private final String DB_USER = "duongbui";
    private final String DB_PASS = "12345";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            LeaveRequestDAO dao = new LeaveRequestDAO(conn);
            List<LeaveRequest> list;

            if (userHasAccessScope(conn, user.getUserId())) {
                list = dao.getRequestsByAccessScope(user); // super manager
            } else if (isManager(conn, user.getUserId())) {
                list = dao.getRequestsByManager(user);
            } else {
                list = dao.getRequestsByUser(user);
            }

            request.setAttribute("requests", list);
            request.setAttribute("pageContent", "leave-list-content.jsp");
            request.getRequestDispatcher("main.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    private boolean userHasAccessScope(Connection conn, int userId) throws Exception {
        PreparedStatement ps = conn.prepareStatement("SELECT 1 FROM Access_Scope WHERE accessor_id = ?");
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    private boolean isManager(Connection conn, int userId) throws Exception {
        PreparedStatement ps = conn.prepareStatement("""
            SELECT 1 FROM User_Roles ur
            JOIN Roles r ON ur.role_id = r.role_id
            WHERE ur.user_id = ? AND r.role_name = 'Manager'
        """);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }
}
