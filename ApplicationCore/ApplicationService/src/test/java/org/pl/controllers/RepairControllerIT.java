package org.pl.controllers;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.microshed.testing.SharedContainerConfig;
import org.microshed.testing.jupiter.MicroShedTest;
import org.pl.model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

@MicroShedTest
@SharedContainerConfig(AppContainerConfig.class)
public class RepairControllerIT {
    private Repair testRepair;
    private Client testClient;
    private Hardware testHardware;
    private String repairId;
    private String repairETag;
    private HardwareType testHardwareType;

    private static String retrieveToken() {
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

    private String retrieveRepairId() {
        return given()
                .contentType(ContentType.JSON)
                .body(testRepair)
                .header("Authorization", "Bearer " + retrieveToken())
                .when()
                .post("/api/repair")
                .then()
                .assertThat()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .extract()
                .path("id");
    }

    private String retrieveRepairETag() {
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + retrieveToken())
                .when()
                .get("/api/repair/id/" + repairId)
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .headers()
                .getValue("ETag");
    }

    @BeforeEach
    void setup() throws ParseException {
        testHardwareType = Console.builder()
                .condition(Condition.FINE)
                .type("CONSOLE")
                .build();

        testHardware = Hardware.builder()
                .archive(false)
                .price(100)
                .hardwareType(testHardwareType)
                .build();

        testClient = Client.builder()
                .id(UUID.randomUUID())
                .address(Address
                        .builder()
                        .city("Lodz")
                        .number("123")
                        .street("Narutowicza")
                        .build())
                .archive(false)
                .balance(100.0)
                .clientAccessType(ClientAccessType.USERS)
                .clientType(new Basic())
                .firstName("Janusz")
                .lastName("Kowalski")
                .phoneNumber("123456789")
                .username("januszkowalski")
                .password("januszek")
                .build();

        testRepair = Repair.builder()
                .id(UUID.randomUUID())
                .archive(false)
                .hardware(testHardware)
                .client(testClient)
                .dateRange(new DateRange(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse("13-02-2020 12:10:10"),
                        new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse("15-02-2020 12:10:10")))
                .build();

        repairId = retrieveRepairId();
        repairETag = retrieveRepairETag();
    }
}
