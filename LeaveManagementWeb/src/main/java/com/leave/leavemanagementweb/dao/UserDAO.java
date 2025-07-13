package com.leave.leavemanagementweb.dao;

import com.leave.leavemanagementweb.model.User;
import com.leave.leavemanagementweb.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public static User checkLogin(String username, String password) {
        String sql = "SELECT user_id, username, full_name FROM Users WHERE username = ? AND password = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password); // Chưa mã hóa để dễ test
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("full_name")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
