package com.leave.leavemanagementweb.controller;

import com.leave.leavemanagementweb.dao.LeaveRequestDAO;
import com.leave.leavemanagementweb.model.LeaveRequest;
import com.leave.leavemanagementweb.model.User;
import com.leave.leavemanagementweb.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

@WebServlet("/view-requests")
public class ViewRequestsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        List<LeaveRequest> list = null;
        try (Connection conn = DBConnection.getConnection()) {
            LeaveRequestDAO dao = new LeaveRequestDAO(conn);
            if (userHasAccessScope(conn, user.getUserId())) {
                list = dao.getRequestsByAccessScope(user);
            } else if (isManager(conn, user.getUserId())) {
                list = dao.getRequestsByManager(user);
            } else {
                list = dao.getRequestsByUser(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        req.setAttribute("requests", list);
        req.setAttribute("pageContent", "view-requests.jsp");
        req.getRequestDispatcher("main.jsp").forward(req, resp);
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
