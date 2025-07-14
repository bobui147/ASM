package com.leave.leavemanagementweb.controller;

import com.leave.leavemanagementweb.dao.LeaveRequestDAO;
import com.leave.leavemanagementweb.model.LeaveRequest;
import com.leave.leavemanagementweb.model.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("/approval")
public class ApprovalServlet extends HttpServlet {
    private LeaveRequestDAO requestDAO = new LeaveRequestDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int requestId = Integer.parseInt(request.getParameter("id"));
        LeaveRequest leaveRequest = requestDAO.getRequestById(requestId);
        request.setAttribute("request", leaveRequest);
        request.getRequestDispatcher("approval-form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int requestId = Integer.parseInt(request.getParameter("requestId"));
        String action = request.getParameter("action");
        String note = request.getParameter("note");

        User user = (User) request.getSession().getAttribute("user");
        requestDAO.processRequest(requestId, action, note, user.getUserId());

        response.sendRedirect("view-requests");
    }
}
