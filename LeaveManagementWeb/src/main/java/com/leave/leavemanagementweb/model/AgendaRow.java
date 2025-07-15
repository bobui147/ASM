package com.leave.leavemanagementweb.model;

import java.util.List;

public class AgendaRow {
    private String employeeName;
    private List<String> statuses; // "Working" hoặc "OnLeave"

    public AgendaRow(String employeeName, List<String> statuses) {
        this.employeeName = employeeName;
        this.statuses = statuses;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public List<String> getStatuses() {
        return statuses;
    }
} 