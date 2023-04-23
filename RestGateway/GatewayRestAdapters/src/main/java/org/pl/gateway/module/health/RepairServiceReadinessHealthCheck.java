package org.pl.gateway.module.health;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

@Readiness
public class RepairServiceReadinessHealthCheck implements HealthCheck {
    private boolean isHealthy() {
        try {
            String url = "https://localhost:8181/RestAdapters-1.0-SNAPSHOT/api/health";
            Client client = ClientBuilder.newClient();
            Response response = client.target(url).request().get();
            return response.getStatus() == 200;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public HealthCheckResponse call() {
        if (!isHealthy()) {
            return HealthCheckResponse.named("Repair service health check readiness")
                    .down()
                    .build();
        } else {
            return HealthCheckResponse.named("Repair service health check readiness")
                    .up()
                    .build();
        }
    }
}
