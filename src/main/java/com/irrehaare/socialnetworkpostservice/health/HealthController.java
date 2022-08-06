package com.irrehaare.socialnetworkpostservice.health;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@AllArgsConstructor
@RestController
public class HealthController {

    private HealthService healthService;

    @GetMapping("/health")
    public HealthResponse getHealth() {
        final HealthResponse health = healthService.getHealth();
        log.debug(String.format("Providing full health status %s", health.isHealthy()));
        return health;
    }

    @GetMapping("is-healthy")
    public boolean isHealthy() {
        final HealthResponse health = healthService.getHealth();
        log.debug(String.format("Providing full health status %s", health.isHealthy()));
        return health.isHealthy();
    }
}
