package com.irrehaare.socialnetworkpostservice.health;

import lombok.Getter;

import java.util.List;

@Getter
public class HealthResponse {
    private final boolean isHealthy;
    private final List<Dependency> dependencies;
    private final String repositoryUrl;
    private final String description;
    private final String impact;

    public HealthResponse(boolean isHealthy,
                          String repositoryUrl,
                          List<Dependency> dependencies,
                          String description,
                          String impact) {
        this.isHealthy = isHealthy;
        this.repositoryUrl = repositoryUrl;
        this.dependencies = dependencies;
        this.description = description;
        this.impact = impact;
    }
}
