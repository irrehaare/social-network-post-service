package com.irrehaare.socialnetworkpostservice.health;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

class HealthServiceTest {
    private static final int DEPENDENCY_COUNT = 3;

    @InjectMocks
    HealthService healthService;

    @Test
    void getHealthDependencyCountCheck() {
        assertEquals(DEPENDENCY_COUNT, healthService.getHealth().getDependencies().size());
    }

    // More tests would follow if the health service was using clients to actual microservices
}