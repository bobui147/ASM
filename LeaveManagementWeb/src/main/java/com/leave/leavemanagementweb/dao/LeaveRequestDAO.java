package com.leave.leavemanagementweb.dao;

import com.leave.leavemanagementweb.model.LeaveRequest;
import com.leave.leavemanagementweb.model.User;
import com.leave.leavemanagementweb.util.DBConnection;

import java.sql.*;
import java.util.*;

public class LeaveRequestDAO {

    private Connection conn;

    public LeaveRequestDAO() {}

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
            lr.setProcessedReason(rs.getString("processed_reason")); // ✅ BỔ SUNG
            list.add(lr);
        }
        return list;
    }

    public static List<LeaveRequest> getRequestsByUserScope(User user) {
        List<LeaveRequest> list = new ArrayList<>();
        String sql = """
            SELECT lr.request_id, lr.start_date, lr.end_date, lr.reason, lr.status,
                   lr.processed_reason,                            -- ✅ thêm
                   u.full_name AS requester_name
            FROM Leave_Requests lr
            JOIN Users u ON lr.user_id = u.user_id
            WHERE lr.user_id = ? 
               OR lr.user_id IN (SELECT user_id FROM Users WHERE manager_id = ?)
               OR ? IN (
                   SELECT accessor_id FROM Access_Scope 
                   WHERE target_user_id = lr.user_id OR target_department_id = u.department_id
               )
        """;
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, user.getUserId()); // chính mình
            ps.setInt(2, user.getUserId()); // cấp dưới
            ps.setInt(3, user.getUserId()); // trong Access_Scope

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LeaveRequest r = new LeaveRequest();
                r.setRequestId(rs.getInt("request_id"));
                r.setStartDate(rs.getDate("start_date"));
                r.setEndDate(rs.getDate("end_date"));
                r.setReason(rs.getString("reason"));
                r.setStatus(rs.getString("status"));
                r.setRequesterName(rs.getString("requester_name"));
                r.setProcessedReason(rs.getString("processed_reason")); // ✅ THÊM
                list.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public LeaveRequest getRequestById(int requestId) {
        String sql = "SELECT r.*, u.full_name FROM Leave_Requests r JOIN Users u ON r.user_id = u.user_id WHERE r.request_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, requestId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                LeaveRequest req = new LeaveRequest();
                req.setRequestId(rs.getInt("request_id"));
                req.setUserId(rs.getInt("user_id"));
                req.setEmployeeName(rs.getString("full_name"));
                req.setStartDate(rs.getDate("start_date"));
                req.setEndDate(rs.getDate("end_date"));
                req.setReason(rs.getString("reason"));
                req.setStatus(rs.getString("status"));
                req.setProcessedReason(rs.getString("processed_reason")); // ✅ THÊM
                return req;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void processRequest(int requestId, String status, String note, int processorId) {
        String sql = "UPDATE Leave_Requests SET status = ?, processed_by = ?, processed_reason = ?, updated_at = GETDATE() WHERE request_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, processorId);
            ps.setString(3, note);
            ps.setInt(4, requestId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
