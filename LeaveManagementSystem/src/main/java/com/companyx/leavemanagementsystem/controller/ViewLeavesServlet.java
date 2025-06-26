package com.companyx.leavemanagementsystem.controller;

import com.companyx.leavemanagementsystem.model.LeaveRequest;
import com.companyx.leavemanagementsystem.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/viewLeaves")
public class ViewLeavesServlet extends HttpServlet {
    private EntityManagerFactory emf;

    @Override
    public void init() {
        emf = Persistence.createEntityManagerFactory("LeaveManagementPU");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        EntityManager em = emf.createEntityManager();
        try {
            List<LeaveRequest> leaveRequests;
            if ("Quản lý".equals(getRoleName(user, em))) {
                leaveRequests = em.createQuery(
                    "SELECT l FROM LeaveRequest l WHERE l.user.manager.userId = :userId OR l.user.userId = :userId",
                    LeaveRequest.class)
                    .setParameter("userId", user.getUserId())
                    .getResultList();
            } else {
                leaveRequests = em.createQuery("SELECT l FROM LeaveRequest l WHERE l.user.userId = :userId", LeaveRequest.class)
                    .setParameter("userId", user.getUserId())
                    .getResultList();
            }

            request.setAttribute("leaveRequests", leaveRequests);
            request.getRequestDispatcher("viewLeaves.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Không thể tải danh sách đơn nghỉ phép");
            request.getRequestDispatcher("viewLeaves.jsp").forward(request, response);
        } finally {
            em.close();
        }
    }

    private String getRoleName(User user, EntityManager em) {
        List<?> roles = em.createQuery("SELECT ur.role.roleName FROM UserRole ur WHERE ur.user.userId = :userId")
                .setParameter("userId", user.getUserId())
                .getResultList();
        return roles.isEmpty() ? "" : (String) roles.get(0);
    }

    @Override
    public void destroy() {
        emf.close();
    }
}