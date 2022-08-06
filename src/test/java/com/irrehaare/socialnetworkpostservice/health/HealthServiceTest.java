package com.irrehaare.socialnetworkpostservice.health;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HealthServiceTest {
    private static final int DEPENDENCY_COUNT = 3;

    @InjectMocks
    HealthService healthService = new HealthService();

    @Test
    void getHealthDependencyCountCheck() {
        assertEquals(DEPENDENCY_COUNT, healthService.getHealth().getDependencies().size());
    }

    // More tests would follow if the health service was using clients to actual microservices
}