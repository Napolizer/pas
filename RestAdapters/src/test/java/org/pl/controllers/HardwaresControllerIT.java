package org.pl.controllers;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.microshed.testing.SharedContainerConfig;
import org.microshed.testing.jupiter.MicroShedTest;
import org.pl.model.*;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@MicroShedTest
@SharedContainerConfig(AppContainerConfig.class)
public class HardwaresControllerIT {
    private HardwareType testComputer;
    private HardwareType testConsole;
    private Hardware testHardware;
    private Hardware testHardware2;

    private String retrieveToken() {
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("username", "admin");
        credentials.put("password", "password");
        return given()
                .contentType(ContentType.JSON)
                .body(credentials)
                .when()
                .post("/api/client/login")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("token", is(instanceOf(String.class)))
                .extract()
                .path("token");
    }

    @Test
    void getAllHardwaresPositiveTest() {
        testComputer = Computer.builder()
                        .condition(Condition.FINE)
                        .type("COMPUTER")
                        .build();
        testConsole = Console.builder()
                        .condition(Condition.AVERAGE)
                        .type("CONSOLE")
                        .build();
        testHardware = Hardware.builder()
                        .archive(false)
                                .price(100)
                                        .hardwareType(testComputer)
                                                .build();
        testHardware2 = Hardware.builder()
                        .archive(false)
                                .price(200)
                                        .hardwareType(testConsole)
                                                .build();
        given()
                .contentType(ContentType.JSON)
                .body(testHardware)
                .header("Authorization", "Bearer " + retrieveToken())
                .when()
                .post("/api/hardware")
                .then()
                .assertThat()
                .statusCode(201);
        given()
                .contentType(ContentType.JSON)
                .body(testHardware2)
                .header("Authorization", "Bearer " + retrieveToken())
                .when()
                .post("/api/hardware")
                .then()
                .assertThat()
                .statusCode(201);
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + retrieveToken())
                .when()
                .get("/api/hardwares")
                .then()
                .assertThat()
                .statusCode(200)
                .body("price", hasItems(100, 200))
                .body("hardwareType.type", hasItems("COMPUTER", "CONSOLE"));
    }

}
