package com.leave.leavemanagementweb.dao;

import com.leave.leavemanagementweb.model.AgendaRow;
import com.leave.leavemanagementweb.util.DBConnection;
import java.sql.*;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.DayOfWeek;

public class AgendaDAO {
    public static List<AgendaRow> getAgendaRows(List<String> dates) {
        List<AgendaRow> result = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            // Lấy danh sách nhân sự
            String userSql = "SELECT user_id, full_name FROM Users";
            Statement userStmt = conn.createStatement();
            ResultSet userRs = userStmt.executeQuery(userSql);
            while (userRs.next()) {
                int userId = userRs.getInt("user_id");
                String name = userRs.getString("full_name");
                List<String> statuses = new ArrayList<>();
                for (String d : dates) {
                    DateTimeFormatter dmy = DateTimeFormatter.ofPattern("d/M/yyyy");
                    LocalDate date = LocalDate.parse(d, dmy);
                    DayOfWeek dow = date.getDayOfWeek();
                    if (dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY) {
                        statuses.add(""); // Không tô màu gì
                    } else {
                        // Kiểm tra có đơn nghỉ phép đã duyệt không
                        String sql = "SELECT 1 FROM Leave_Requests WHERE user_id=? AND status='Approved' AND start_date<=? AND end_date>=?";
                        try (PreparedStatement ps = conn.prepareStatement(sql)) {
                            ps.setInt(1, userId);
                            ps.setDate(2, java.sql.Date.valueOf(date));
                            ps.setDate(3, java.sql.Date.valueOf(date));
                            ResultSet rs = ps.executeQuery();
                            if (rs.next()) {
                                statuses.add("OnLeave");
                            } else {
                                statuses.add("Working");
                            }
                        }
                    }
                }
                result.add(new AgendaRow(name, statuses));
            }
            userRs.close();
            userStmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
} 