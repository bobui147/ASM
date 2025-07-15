package com.leave.leavemanagementweb.dao;

import com.leave.leavemanagementweb.model.AgendaRow;
import com.leave.leavemanagementweb.util.DBConnection;
import java.sql.*;
import java.util.*;

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
                    // Chuyển d ("1/1") thành yyyy-MM-dd (ví dụ: 2024-01-01)
                    String[] parts = d.split("/");
                    String dateStr = String.format("2024-%02d-%02d", Integer.parseInt(parts[1]), Integer.parseInt(parts[0]));
                    String agendaSql = "SELECT status FROM Agenda WHERE user_id=? AND date=?";
                    PreparedStatement agendaStmt = conn.prepareStatement(agendaSql);
                    agendaStmt.setInt(1, userId);
                    agendaStmt.setDate(2, java.sql.Date.valueOf(dateStr));
                    ResultSet agendaRs = agendaStmt.executeQuery();
                    if (agendaRs.next()) {
                        statuses.add(agendaRs.getString("status"));
                    } else {
                        statuses.add("Working"); // Mặc định nếu không có bản ghi là đi làm
                    }
                    agendaRs.close();
                    agendaStmt.close();
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