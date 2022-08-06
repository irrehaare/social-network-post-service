package com.irrehaare.socialnetworkpostservice.health;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Dependency {
    private final String name;
    private final String description;
    private final boolean isHealthy;

    public Dependency(String name, String description, boolean isHealthy) {
        this.name = Objects.requireNonNull(name, "Name can't be null");
        this.description = Objects.requireNonNull(description, "Description can't be null");
        this.isHealthy = isHealthy;
    }


}
