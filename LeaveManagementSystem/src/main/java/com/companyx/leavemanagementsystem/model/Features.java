package com.companyx.leavemanagementsystem.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Features")
public class Features {
    @Id
    @Column(name = "feature_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int featureId;

    @Column(name = "feature_name", nullable = false)
    private String featureName;

    @Column(name = "entrypoint")
    private String entrypoint;

    // Getters và Setters
    public int getFeatureId() { return featureId; }
    public void setFeatureId(int featureId) { this.featureId = featureId; }
    public String getFeatureName() { return featureName; }
    public void setFeatureName(String featureName) { this.featureName = featureName; }
    public String getEntrypoint() { return entrypoint; }
    public void setEntrypoint(String entrypoint) { this.entrypoint = entrypoint; }
}