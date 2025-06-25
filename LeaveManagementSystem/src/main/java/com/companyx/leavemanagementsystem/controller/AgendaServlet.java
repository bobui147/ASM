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
import java.util.*;

@WebServlet("/agenda")
public class AgendaServlet extends HttpServlet {
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
        if (user == null || !"Quản lý".equals(user.getRole().getRoleName())) {
            response.sendRedirect("login.jsp");
            return;
        }

        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");

        if (startDateStr == null || endDateStr == null) {
            request.getRequestDispatcher("agenda.jsp").forward(request, response);
            return;
        }

        EntityManager em = emf.createEntityManager();
        try {
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateStr);
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateStr);

            List<User> employees = em.createQuery("SELECT u FROM User u WHERE u.department.departmentID = :deptID", User.class)
                    .setParameter("deptID", user.getDepartment().getDepartmentID())
                    .getResultList();

            List<LeaveRequest> leaves = em.createQuery(
                "SELECT l FROM LeaveRequest l WHERE l.status = 'Approved' AND l.fromDate <= :endDate AND l.toDate >= :startDate",
                LeaveRequest.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();

            List<String> dates = new ArrayList<>();
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            while (!cal.getTime().after(endDate)) {
                dates.add(sdf.format(cal.getTime()));
                cal.add(Calendar.DAY_OF_MONTH, 1);
            }

            Map<Integer, Map<String, Boolean>> leaveMap = new HashMap<>();
            for (User emp : employees) {
                Map<String, Boolean> dateMap = new HashMap<>();
                for (String date : dates) {
                    dateMap.put(date, false);
                }
                leaveMap.put(emp.getUserID(), dateMap);
            }

            for (LeaveRequest leave : leaves) {
                cal.setTime(leave.getFromDate());
                while (!cal.getTime().after(leave.getToDate())) {
                    String dateStr = sdf.format(cal.getTime());
                    leaveMap.get(leave.getUser().getUserID()).put(dateStr, true);
                    cal.add(Calendar.DAY_OF_MONTH, 1);
                }
            }

            request.setAttribute("employees", employees);
            request.setAttribute("dates", dates);
            request.setAttribute("leaveMap", leaveMap);
            request.getRequestDispatcher("agenda.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Không thể tải lịch agenda");
            request.getRequestDispatcher("agenda.jsp").forward(request, response);
        } finally {
            em.close();
        }
    }

    @Override
    public void destroy() {
        emf.close();
    }
}