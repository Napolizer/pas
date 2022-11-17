package org.pl.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.pl.model.Address;
import org.pl.model.Client;
import org.pl.model.Hardware;
import org.pl.model.HardwareType;
import org.pl.services.ClientService;

import static org.junit.jupiter.api.Assertions.*;

public class HardwareControllerTest {
    private EntityManagerFactory emf;
    private EntityManager em;
    private HardwareType computer;
    private Hardware hardware1, hardware2;
    private ClientService clientService;
    final String BASE_URL = "http://localhost:8080/pobi-java-1.0-SNAPSHOT/api";
    private jakarta.ws.rs.client.Client client;
}
