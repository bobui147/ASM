package com.companyx.leavemanagementsystem.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "LeaveRequests")
public class LeaveRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int leaveID;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;

    private String title;

    @Column(nullable = false)
    private Date fromDate;

    @Column(nullable = false)
    private Date toDate;

    private String reason;

    @Column(columnDefinition = "NVARCHAR(20) DEFAULT 'Inprogress'")
    private String status;

    @ManyToOne
    @JoinColumn(name = "ProcessedByID")
    private User processedBy;

    private String processedReason;

    @Column(columnDefinition = "DATETIME DEFAULT GETDATE()")
    private Date createdAt;

    // Getters và Setters
    public int getLeaveID() { return leaveID; }
    public void setLeaveID(int leaveID) { this.leaveID = leaveID; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Date getFromDate() { return fromDate; }
    public void setFromDate(Date fromDate) { this.fromDate = fromDate; }
    public Date getToDate() { return toDate; }
    public void setToDate(Date toDate) { this.toDate = toDate; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public User getProcessedBy() { return processedBy; }
    public void setProcessedBy(User processedBy) { this.processedBy = processedBy; }
    public String getProcessedReason() { return processedReason; }
    public void setProcessedReason(String processedReason) { this.processedReason = processedReason; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}