package com.leave.leavemanagementweb.controller;

import com.leave.leavemanagementweb.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

@WebServlet("/leave-request")
public class LeaveRequestServlet extends HttpServlet {

    private final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=LeaveManagement;encrypt=true;trustServerCertificate=true";
    private final String DB_USER = "duongbui";
    private final String DB_PASS = "12345";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy user từ session
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Nhận dữ liệu từ form
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String reason = request.getParameter("reason");

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String sql = "INSERT INTO Leave_Requests (user_id, start_date, end_date, reason, status, created_at, updated_at) "
                    + "VALUES (?, ?, ?, ?, 'Inprogress', GETDATE(), GETDATE())";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, user.getUserId());
            stmt.setDate(2, Date.valueOf(LocalDate.parse(startDate)));
            stmt.setDate(3, Date.valueOf(LocalDate.parse(endDate)));
            stmt.setString(4, reason);

            stmt.executeUpdate();

            // Gửi lại về dashboard sau khi lưu
            response.sendRedirect("dashboard.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error submitting leave request.");
            request.getRequestDispatcher("leave-request.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String sql = """
            SELECT r.role_name, d.department_name
            FROM Users u
            JOIN User_Roles ur ON u.user_id = ur.user_id
            JOIN Roles r ON ur.role_id = r.role_id
            JOIN Departments d ON u.department_id = d.department_id
            WHERE u.user_id = ?
        """;

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, user.getUserId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                request.setAttribute("role", rs.getString("role_name"));
                request.setAttribute("department", rs.getString("department_name"));
            }

            request.setAttribute("pageContent", "leave-request-content.jsp");
            request.getRequestDispatcher("main.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

}
