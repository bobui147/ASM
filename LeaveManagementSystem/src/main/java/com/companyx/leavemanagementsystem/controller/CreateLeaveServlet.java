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
import java.text.SimpleDateFormat;

@WebServlet("/createLeave")
public class CreateLeaveServlet extends HttpServlet {
    private EntityManagerFactory emf;

    @Override
    public void init() {
        emf = Persistence.createEntityManagerFactory("LeaveManagementPU");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String title = request.getParameter("title");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        String reason = request.getParameter("reason");

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            LeaveRequest leave = new LeaveRequest();
            leave.setUser(user);
            leave.setTitle(title);
            leave.setFromDate(new SimpleDateFormat("yyyy-MM-dd").parse(fromDate));
            leave.setToDate(new SimpleDateFormat("yyyy-MM-dd").parse(toDate));
            leave.setReason(reason);
            leave.setStatus("Inprogress");
            em.persist(leave);
            em.getTransaction().commit();
            response.sendRedirect("viewLeaves");
        } catch (Exception e) {
            em.getTransaction().rollback();
            request.setAttribute("error", "Không thể tạo đơn nghỉ phép");
            request.getRequestDispatcher("createLeave.jsp").forward(request, response);
        } finally {
            em.close();
        }
    }

    @Override
    public void destroy() {
        emf.close();
    }
}