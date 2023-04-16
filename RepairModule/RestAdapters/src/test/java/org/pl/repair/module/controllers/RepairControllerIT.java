package org.pl.repair.module.controllers;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.microshed.testing.SharedContainerConfig;
import org.microshed.testing.jupiter.MicroShedTest;
import org.pl.repair.module.model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

@MicroShedTest
@SharedContainerConfig(AppContainerConfig.class)
public class RepairControllerIT {
    private RepairRest testRepair;
    private ClientRest testClient;
    private HardwareRest testHardware;
    private String repairId;
    private String repairETag;
    private HardwareTypeRest testHardwareType;

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
        testHardwareType = ConsoleRest.builder()
                .condition(ConditionRest.FINE)
                .type("CONSOLE")
                .build();

        testHardware = HardwareRest.builder()
                .archive(false)
                .price(100)
                .hardwareType(testHardwareType)
                .build();

        testClient = ClientRest.builder()
                .id(UUID.randomUUID())
                .address(AddressRest
                        .builder()
                        .city("Lodz")
                        .number("123")
                        .street("Narutowicza")
                        .build())
                .archive(false)
                .balance(100.0)
                .clientAccessType(ClientAccessTypeRest.USERS)
                .clientType(new BasicRest())
                .firstName("Janusz")
                .lastName("Kowalski")
                .phoneNumber("123456789")
                .username("januszkowalski")
                .build();

        testRepair = RepairRest.builder()
                .id(UUID.randomUUID())
                .archive(false)
                .hardware(testHardware)
                .client(testClient)
                .dateRange(new DateRangeRest(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse("13-02-2020 12:10:10"),
                        new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse("15-02-2020 12:10:10")))
                .build();

//        repairId = retrieveRepairId();
//        repairETag = retrieveRepairETag();
    }
}
