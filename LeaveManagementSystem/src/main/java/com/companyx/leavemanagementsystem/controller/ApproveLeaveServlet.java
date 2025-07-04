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

@WebServlet("/approveLeave")
public class ApproveLeaveServlet extends HttpServlet {
    private EntityManagerFactory emf;

    @Override
    public void init() {
        emf = Persistence.createEntityManagerFactory("LeaveManagementPU");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (user == null || !"Quản lý".equals(getRoleName(user))) {
            request.setAttribute("error", "Bạn không có quyền truy cập trang này.");
            request.getRequestDispatcher("viewLeaves.jsp").forward(request, response);
            return;
        }

        String requestIdParam = request.getParameter("requestId");
        if (requestIdParam == null || requestIdParam.trim().isEmpty()) {
            request.setAttribute("error", "ID đơn nghỉ phép không hợp lệ.");
            request.getRequestDispatcher("viewLeaves.jsp").forward(request, response);
            return;
        }

        int requestId;
        try {
            requestId = Integer.parseInt(requestIdParam);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID đơn nghỉ phép không hợp lệ.");
            request.getRequestDispatcher("viewLeaves.jsp").forward(request, response);
            return;
        }

        EntityManager em = emf.createEntityManager();
        try {
            LeaveRequest leave = em.find(LeaveRequest.class, requestId);
            if (leave == null) {
                request.setAttribute("error", "Đơn nghỉ phép không tồn tại.");
                request.getRequestDispatcher("viewLeaves.jsp").forward(request, response);
                return;
            }

            User manager = leave.getUser().getManager();
            if (manager == null || manager.getUserId() != user.getUserId()) {
                request.setAttribute("error", "Bạn không có quyền duyệt đơn này.");
                request.getRequestDispatcher("viewLeaves.jsp").forward(request, response);
                return;
            }

            request.setAttribute("leave", leave);
            request.getRequestDispatcher("approveLeave.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Lỗi khi tải đơn nghỉ phép: " + e.getMessage());
            request.getRequestDispatcher("viewLeaves.jsp").forward(request, response);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (user == null || !"Quản lý".equals(getRoleName(user))) {
            request.setAttribute("error", "Bạn không có quyền truy cập trang này.");
            request.getRequestDispatcher("viewLeaves.jsp").forward(request, response);
            return;
        }

        String requestIdParam = request.getParameter("requestId");
        if (requestIdParam == null || requestIdParam.trim().isEmpty()) {
            request.setAttribute("error", "ID đơn nghỉ phép không hợp lệ.");
            request.getRequestDispatcher("approveLeave.jsp").forward(request, response);
            return;
        }

        int requestId;
        try {
            requestId = Integer.parseInt(requestIdParam);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID đơn nghỉ phép không hợp lệ.");
            request.getRequestDispatcher("approveLeave.jsp").forward(request, response);
            return;
        }

        String action = request.getParameter("action");
        if (action == null || (!"approve".equals(action) && !"reject".equals(action))) {
            request.setAttribute("error", "Hành động không hợp lệ.");
            request.getRequestDispatcher("approveLeave.jsp").forward(request, response);
            return;
        }

        String processedReason = request.getParameter("processedReason");

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            LeaveRequest leave = em.find(LeaveRequest.class, requestId);
            if (leave == null) {
                em.getTransaction().rollback();
                request.setAttribute("error", "Đơn nghỉ phép không tồn tại.");
                request.getRequestDispatcher("approveLeave.jsp").forward(request, response);
                return;
            }

            User manager = leave.getUser().getManager();
            if (manager == null || manager.getUserId() != user.getUserId()) {
                em.getTransaction().rollback();
                request.setAttribute("error", "Bạn không có quyền duyệt đơn này.");
                request.getRequestDispatcher("approveLeave.jsp").forward(request, response);
                return;
            }

            leave.setProcessedBy(user);
            leave.setProcessedReason(processedReason);
            leave.setStatus("approve".equals(action) ? "Approved" : "Rejected");
            em.merge(leave);
            em.getTransaction().commit();
            response.sendRedirect("viewLeaves");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            request.setAttribute("error", "Lỗi khi xử lý đơn nghỉ phép: " + e.getMessage());
            request.getRequestDispatcher("approveLeave.jsp").forward(request, response);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    private String getRoleName(User user) {
        EntityManager em = emf.createEntityManager();
        try {
            List<?> roles = em.createQuery("SELECT ur.role.roleName FROM UserRole ur WHERE ur.user.userId = :userId")
                    .setParameter("userId", user.getUserId())
                    .getResultList();
            return roles.isEmpty() ? "" : (String) roles.get(0);
        } finally {
            em.close();
        }
    }

    @Override
    public void destroy() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}