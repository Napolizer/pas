package org.pl.adapter.data.model;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class User {
    private String username = "john";

    public String getUsername() {
        return username;
    }
}
