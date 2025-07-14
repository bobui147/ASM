package com.leave.leavemanagementweb.controller;

import com.leave.leavemanagementweb.dao.LeaveRequestDAO;
import com.leave.leavemanagementweb.model.LeaveRequest;
import com.leave.leavemanagementweb.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
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

        List<LeaveRequest> requests = LeaveRequestDAO.getRequestsByUserScope(user);
        req.setAttribute("requests", requests);
        req.setAttribute("pageContent", "view-requests.jsp");
        req.getRequestDispatcher("main.jsp").forward(req, resp);
    }
}
