package org.pl.adapter.data.model;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class User {
    private String name = "John";

    public String getName() {
        return name;
    }
}
