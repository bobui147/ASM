package com.companyx.leavemanagementsystem.controller;

import com.companyx.leavemanagementsystem.model.Agenda;
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
        if (user == null || !"Quản lý".equals(getRoleName(user))) {
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

            List<User> employees = em.createQuery("SELECT u FROM User u WHERE u.department.departmentId = :deptId", User.class)
                    .setParameter("deptId", user.getDepartment().getDepartmentId())
                    .getResultList();

            List<LeaveRequest> leaves = em.createQuery(
                    "SELECT l FROM LeaveRequest l WHERE l.status = 'Approved' AND l.startDate <= :endDate AND l.endDate >= :startDate",
                    LeaveRequest.class)
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .getResultList();

            List<Date> dates = new ArrayList<>();
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            while (!cal.getTime().after(endDate)) {
                dates.add(cal.getTime());
                cal.add(Calendar.DAY_OF_MONTH, 1);
            }

            Map<Integer, Map<Date, String>> agendaMap = new HashMap<>();
            for (User emp : employees) {
                Map<Date, String> dateMap = new HashMap<>();
                for (Date date : dates) {
                    dateMap.put(date, "Working");
                }
                agendaMap.put(emp.getUserId(), dateMap);
            }

            for (LeaveRequest leave : leaves) {
                cal.setTime(leave.getStartDate());
                while (!cal.getTime().after(leave.getEndDate())) {
                    Date date = cal.getTime();
                    agendaMap.get(leave.getUser().getUserId()).put(date, "OnLeave");
                    cal.add(Calendar.DAY_OF_MONTH, 1);
                }
            }

            // Lưu vào bảng Agenda
            em.getTransaction().begin();
            for (Map.Entry<Integer, Map<Date, String>> entry : agendaMap.entrySet()) {
                int userId = entry.getKey();
                for (Map.Entry<Date, String> dateEntry : entry.getValue().entrySet()) {
                    Agenda agenda = em.createQuery("SELECT a FROM Agenda a WHERE a.user.userId = :userId AND a.date = :date", Agenda.class)
                            .setParameter("userId", userId)
                            .setParameter("date", dateEntry.getKey())
                            .getResultList().stream().findFirst().orElse(new Agenda());
                    agenda.setUser(em.find(User.class, userId));
                    agenda.setDate(dateEntry.getKey());
                    agenda.setStatus(dateEntry.getValue());
                    if (agenda.getAgendaId() == 0) {
                        em.persist(agenda);
                    } else {
                        em.merge(agenda);
                    }
                }
            }
            em.getTransaction().commit();

            request.setAttribute("employees", employees);
            request.setAttribute("dates", dates);
            request.setAttribute("agendaMap", agendaMap);
            request.getRequestDispatcher("agenda.jsp").forward(request, response);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            request.setAttribute("error", "Không thể tải lịch agenda");
            request.getRequestDispatcher("agenda.jsp").forward(request, response);
        } finally {
            em.close();
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
        emf.close();
    }
}