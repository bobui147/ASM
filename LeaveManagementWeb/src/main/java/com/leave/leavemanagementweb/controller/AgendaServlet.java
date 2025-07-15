package com.leave.leavemanagementweb.controller;

import com.leave.leavemanagementweb.dao.AgendaDAO;
import com.leave.leavemanagementweb.model.AgendaRow;
import com.leave.leavemanagementweb.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
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

        // Danh sách ngày mẫu
        List<String> dates = Arrays.asList("1/1", "2/1", "3/1", "4/1", "5/1", "6/1", "7/1", "8/1", "9/1");

        // Lấy dữ liệu thật từ DB
        List<AgendaRow> rows = AgendaDAO.getAgendaRows(dates);

        req.setAttribute("dates", dates);
        req.setAttribute("rows", rows);
        req.setAttribute("pageContent", "agenda.jsp");
        req.getRequestDispatcher("main.jsp").forward(req, resp);
    }
} 