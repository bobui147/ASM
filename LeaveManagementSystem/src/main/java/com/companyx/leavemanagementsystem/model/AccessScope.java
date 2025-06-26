package com.companyx.leavemanagementsystem.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Access_Scope")
public class AccessScope {
    @Id
    @Column(name = "access_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accessId;

    @ManyToOne
    @JoinColumn(name = "accessor_id", nullable = false)
    private User accessor;

    @ManyToOne
    @JoinColumn(name = "target_user_id")
    private User targetUser;

    @ManyToOne
    @JoinColumn(name = "target_department_id")
    private Department targetDepartment;

    @Column(name = "permission_type", nullable = false)
    private String permissionType;

    @Column(name = "expiry_date")
    private Date expiryDate;

    // Getters và Setters
    public int getAccessId() { return accessId; }
    public void setAccessId(int accessId) { this.accessId = accessId; }
    public User getAccessor() { return accessor; }
    public void setAccessor(User accessor) { this.accessor = accessor; }
    public User getTargetUser() { return targetUser; }
    public void setTargetUser(User targetUser) { this.targetUser = targetUser; }
    public Department getTargetDepartment() { return targetDepartment; }
    public void setTargetDepartment(Department targetDepartment) { this.targetDepartment = targetDepartment; }
    public String getPermissionType() { return permissionType; }
    public void setPermissionType(String permissionType) { this.permissionType = permissionType; }
    public Date getExpiryDate() { return expiryDate; }
    public void setExpiryDate(Date expiryDate) { this.expiryDate = expiryDate; }
}