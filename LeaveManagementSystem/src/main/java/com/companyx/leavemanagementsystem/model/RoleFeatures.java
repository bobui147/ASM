package com.companyx.leavemanagementsystem.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Role_Features")
public class RoleFeatures {
    @EmbeddedId
    private RoleFeaturesId id;

    @ManyToOne
    @JoinColumn(name = "role_id", insertable = false, updatable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "feature_id", insertable = false, updatable = false)
    private Features feature;

    // Getters và Setters
    public RoleFeaturesId getId() { return id; }
    public void setId(RoleFeaturesId id) { this.id = id; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public Features getFeature() { return feature; }
    public void setFeature(Features feature) { this.feature = feature; }
}

// Composite key class
@Embeddable
class RoleFeaturesId implements java.io.Serializable {
    @Column(name = "role_id")
    private int roleId;

    @Column(name = "feature_id")
    private int featureId;

    // Default constructor
    public RoleFeaturesId() {}

    // Parameterized constructor
    public RoleFeaturesId(int roleId, int featureId) {
        this.roleId = roleId;
        this.featureId = featureId;
    }

    // Equals and HashCode (required for @Embeddable)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleFeaturesId that = (RoleFeaturesId) o;
        return roleId == that.roleId && featureId == that.featureId;
    }

    @Override
    public int hashCode() {
        return 31 * roleId + featureId;
    }

    // Getters và Setters
    public int getRoleId() { return roleId; }
    public void setRoleId(int roleId) { this.roleId = roleId; }
    public int getFeatureId() { return featureId; }
    public void setFeatureId(int featureId) { this.featureId = featureId; }
}