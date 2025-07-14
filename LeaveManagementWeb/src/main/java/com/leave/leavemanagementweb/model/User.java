package com.leave.leavemanagementweb.model;

public class User {

    private int userId;
    private String username;
    private String fullName;
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User(int userId, String username, String fullName) {
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public User() {
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

}
