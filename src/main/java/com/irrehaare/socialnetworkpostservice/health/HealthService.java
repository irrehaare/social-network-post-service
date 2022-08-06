package com.irrehaare.socialnetworkpostservice.health;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class HealthService {

    /*
    This is just a mock of a health service, usually dependencies should by checked with appropriate clients.
    That is by the assumption that microservices don't function individually.
     */

    private static final String REPOSITORY_URL = "https://github.com/irrehaare/social-network-post-service";
    private static final String DESCRIPTION = "Provides a REST CRUD API for social network posts.";
    private static final String IMPACT = "CRITICAL: No posts can be loaded or edited without it.";

    private List<Dependency> getDependencies() {
        final LinkedList<Dependency> dependenciesList = new LinkedList<>();
        dependenciesList.add(new Dependency(
                "db-connection",
                "Basic requirement for this service to function",
                true));
        dependenciesList.add(new Dependency(
                "authorization-service",
                "In microservice environment could be used to map user IDs to names",
                true));
        dependenciesList.add(new Dependency(
                "user-service",
                "In microservice environment could be used to map user IDs to names",
                true));

        return dependenciesList;
    }

    private boolean getIsHealthy() {
        return getDependencies().parallelStream()
                .allMatch(Dependency::isHealthy);
    }

    public HealthResponse getHealth() {
        return new HealthResponse(getIsHealthy(), REPOSITORY_URL, getDependencies(), DESCRIPTION, IMPACT);
    }
}
