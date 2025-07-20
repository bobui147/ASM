package com.leave.leavemanagementweb.controller;

import com.leave.leavemanagementweb.dao.AgendaDAO;
import com.leave.leavemanagementweb.model.AgendaRow;
import com.leave.leavemanagementweb.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@WebServlet("/agenda")
public class AgendaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null || user.getRole() == null || !"supermanager".equals(user.getRole())) {
            resp.sendRedirect("login.jsp");
            return;
        }

        String startDateStr = req.getParameter("startDate");
        if (startDateStr == null || startDateStr.isEmpty()) {
            startDateStr = "2025-01-01";
        }
        LocalDate startDate = LocalDate.parse(startDateStr);
        DateTimeFormatter dmy = DateTimeFormatter.ofPattern("d/M/yyyy");
        List<String> dates = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            dates.add(startDate.plusDays(i).format(dmy));
        }

        // Lấy dữ liệu thật từ DB
        List<AgendaRow> rows = AgendaDAO.getAgendaRows(dates);

        req.setAttribute("dates", dates);
        req.setAttribute("rows", rows);
        req.setAttribute("pageContent", "agenda.jsp");
        req.getRequestDispatcher("main.jsp").forward(req, resp);
    }
} 