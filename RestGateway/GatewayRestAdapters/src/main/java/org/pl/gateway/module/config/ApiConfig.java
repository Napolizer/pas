package org.pl.gateway.module.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class ApiConfig {
    @Inject
    @ConfigProperty(name="user_api")
    private String userApi;
    @Inject
    @ConfigProperty(name="client_api")
    private String clientApi;
    @Inject
    @ConfigProperty(name="repair_api")
    private String repairApi;

    public String getUserApi() {
        return userApi;
    }

    public String getClientApi() {
        return clientApi;
    }

    public String getRepairApi() {
        return repairApi;
    }
}
