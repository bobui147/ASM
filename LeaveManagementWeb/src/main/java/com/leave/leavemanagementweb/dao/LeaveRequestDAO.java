package com.leave.leavemanagementweb.dao;

import com.leave.leavemanagementweb.model.LeaveRequest;
import com.leave.leavemanagementweb.model.User;

import java.sql.*;
import java.util.*;

public class LeaveRequestDAO {
    private Connection conn;

    public LeaveRequestDAO(Connection conn) {
        this.conn = conn;
    }

    public List<LeaveRequest> getRequestsByUser(User user) throws SQLException {
        String sql = """
            SELECT lr.*, u.full_name AS employee_name, p.full_name AS processed_by_name
            FROM Leave_Requests lr
            JOIN Users u ON lr.user_id = u.user_id
            LEFT JOIN Users p ON lr.processed_by = p.user_id
            WHERE lr.user_id = ?
        """;

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, user.getUserId());
        ResultSet rs = ps.executeQuery();
        return parseResults(rs);
    }

    public List<LeaveRequest> getRequestsByManager(User manager) throws SQLException {
        String sql = """
            SELECT lr.*, u.full_name AS employee_name, p.full_name AS processed_by_name
            FROM Leave_Requests lr
            JOIN Users u ON lr.user_id = u.user_id
            JOIN Users m ON u.manager_id = m.user_id
            LEFT JOIN Users p ON lr.processed_by = p.user_id
            WHERE m.user_id = ?
        """;

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, manager.getUserId());
        ResultSet rs = ps.executeQuery();
        return parseResults(rs);
    }

    public List<LeaveRequest> getRequestsByAccessScope(User superUser) throws SQLException {
        String sql = """
            SELECT lr.*, u.full_name AS employee_name, p.full_name AS processed_by_name
            FROM Leave_Requests lr
            JOIN Users u ON lr.user_id = u.user_id
            LEFT JOIN Users p ON lr.processed_by = p.user_id
            WHERE u.user_id IN (
                SELECT target_user_id FROM Access_Scope 
                WHERE accessor_id = ? AND permission_type IN ('View', 'Process')
            )
            OR u.department_id IN (
                SELECT target_department_id FROM Access_Scope 
                WHERE accessor_id = ? AND permission_type IN ('View', 'Process')
            )
        """;

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, superUser.getUserId());
        ps.setInt(2, superUser.getUserId());
        ResultSet rs = ps.executeQuery();
        return parseResults(rs);
    }

    private List<LeaveRequest> parseResults(ResultSet rs) throws SQLException {
        List<LeaveRequest> list = new ArrayList<>();
        while (rs.next()) {
            LeaveRequest lr = new LeaveRequest();
            lr.setRequestId(rs.getInt("request_id"));
            lr.setUserId(rs.getInt("user_id"));
            lr.setStartDate(rs.getDate("start_date"));
            lr.setEndDate(rs.getDate("end_date"));
            lr.setReason(rs.getString("reason"));
            lr.setStatus(rs.getString("status"));
            lr.setEmployeeName(rs.getString("employee_name"));
            lr.setProcessedByName(rs.getString("processed_by_name"));
            list.add(lr);
        }
        return list;
    }
}
