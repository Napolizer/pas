package org.pl.controllers;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.exceptions.HardwareException;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Computer;
import org.pl.model.Console;
import org.pl.model.Hardware;
import org.pl.services.HardwareService;

import static org.junit.jupiter.api.Assertions.*;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

@WireMockTest(httpPort = 8080, httpsEnabled = true)
class HardwareControllerTest {
    @Inject
    private HardwareService hardwareService;

    private Hardware computer;
    private Hardware console;

    @BeforeEach
    void setup() throws HardwareException, RepositoryException {
        computer = hardwareService.add(Hardware.builder()
                .hardwareType(new Computer())
                .archive(false)
                .price(100)
                .build());
        console = hardwareService.add(Hardware.builder()
                .hardwareType(new Console())
                .archive(false)
                .price(200)
                .build());
    }

    @Test
    void getTest() {
        stubFor(get("/api/hardware/id/" + computer.getId())
                .willReturn(ok()));
    }
}