package org.pl.controllers;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
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
public class HardwareControllerIT {
    private String token = "";
    private HardwareType hardwareType;
    private Hardware testHardware;
    private String hardwareId;
    private String hardwareETag;

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

    private String retrieveHardwareId() {
        return given()
                .contentType(ContentType.JSON)
                .body(testHardware)
                .header("Authorization", "Bearer " + retrieveToken())
                .when()
                .post("/api/hardware")
                .then()
                .assertThat()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .extract()
                .path("id");
    }

    private String retrieveHardwareETag() {
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + retrieveToken())
                .when()
                .get("/api/hardware/id/" + hardwareId)
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .headers()
                .getValue("ETag");
    }

    @BeforeEach
    void beforeEach() {
        hardwareType = Console.builder()
                .condition(Condition.FINE)
                .type("CONSOLE")
                .build();

        testHardware = Hardware.builder()
                .archive(false)
                .price(100)
                .hardwareType(hardwareType)
                .build();

        hardwareId = retrieveHardwareId();
        hardwareETag = retrieveHardwareETag();
    }

    @Test
    void createHardwareMissingBodyTest() {
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + retrieveToken())
                .when()
                .post("/api/hardware")
                .then()
                .assertThat()
                .statusCode(400);
    }

    @Test
    void createHardwareEmptyBodyTest() {
        given()
                .contentType(ContentType.JSON)
                .body("{}")
                .header("Authorization", "Bearer " + retrieveToken())
                .when()
                .post("/api/hardware")
                .then()
                .assertThat()
                .statusCode(400);
    }

    @Test
    void createHardwareMissingParameterArchiveTest() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"price\": \"100\", \"hardwareType\": { \"condition\": \"FINE\", \"type\": \"CONSOLE\"}}")
                .header("Authorization", "Bearer " + retrieveToken())
                .when()
                .post("/api/hardware")
                .then()
                .assertThat()
                .statusCode(400);
    }

    @Test
    void createHardwareMissingParameterPriceTest() {
        testHardware.setPrice(null);
        given()
                .contentType(ContentType.JSON)
                .body(testHardware)
                .header("Authorization", "Bearer " + retrieveToken())
                .when()
                .post("/api/hardware")
                .then()
                .assertThat()
                .statusCode(400);
    }

    @Test
    void createHardwareMissingParameterHardwareTypeTest() {
        testHardware.setHardwareType(null);
        given()
                .contentType(ContentType.JSON)
                .body(testHardware)
                .header("Authorization", "Bearer " + retrieveToken())
                .when()
                .post("/api/hardware")
                .then()
                .assertThat()
                .statusCode(400);
    }

    @Test
    void createHardwareMissingParameterHardwareTypeConditionTest() {
        testHardware.getHardwareType().setCondition(null);
        given()
                .contentType(ContentType.JSON)
                .body(testHardware)
                .header("Authorization", "Bearer " + retrieveToken())
                .when()
                .post("/api/hardware")
                .then()
                .assertThat()
                .statusCode(400);
    }

    @Test
    void createHardwareMissingParameterHardwareTypeTypeTest() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"archive\": \"false\", \"price\": \"100\", \"hardwareType\": { \"condition\": \"FINE\"}}")
                .header("Authorization", "Bearer " + retrieveToken())
                .when()
                .post("/api/hardware")
                .then()
                .assertThat()
                .statusCode(400);
    }

    @Test
    void createHardwarePositiveTest() {
        given()
                .contentType(ContentType.JSON)
                .body(testHardware)
                .header("Authorization", "Bearer " + retrieveToken())
                .when()
                .post("/api/hardware")
                .then()
                .assertThat()
                .statusCode(201)
                .body("archive", equalTo(false))
                .body("price", equalTo(100))
                .body("hardwareType.condition", equalTo("FINE"))
                .body("hardwareType.type", equalTo("CONSOLE"));
    }

    @Test
    void getHardwarePositiveTest() {
        given()
                .header("Authorization", "Bearer " + retrieveToken())
                .when()
                .get("/api/hardware/id/" + hardwareId)
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("archive", equalTo(testHardware.isArchive()))
                .body("price", equalTo(testHardware.getPrice()))
                .body("hardwareType.condition", equalTo(testHardware.getHardwareType().getCondition().toString()))
                .body("hardwareType.type", equalTo(testHardware.getHardwareType().getType()));
    }

    @Test
    void getHardwareMissingIdTest() {
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + retrieveToken())
                .when()
                .get("/api/hardware/id/")
                .then()
                .assertThat()
                .statusCode(404);
    }

    @Test
    void getHardwareHardwareDoesNotExistTest() {
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + retrieveToken())
                .when()
                .get("/api/hardware/id/15f54b07-da96-43e7-b29c-1909eddccf72")
                .then()
                .assertThat()
                .statusCode(404);
    }

    @Test
    void updateHardwareEmptyBodyTest() {
        given()
                .contentType(ContentType.JSON)
                .body("{}")
                .header("Authorization", "Bearer " + retrieveToken())
                .header("If-Match", hardwareETag)
                .when()
                .put("/api/hardware/id/" + hardwareId)
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    void updateHardwareAllFieldsTest() {
        testHardware.setArchive(true);
        testHardware.setPrice(200);
        HardwareType newComputer = Computer.builder()
                .condition(Condition.AVERAGE)
                .type("COMPUTER")
                .build();
        testHardware.setHardwareType(newComputer);
        given()
                .contentType(ContentType.JSON)
                .body(testHardware)
                .header("Authorization", "Bearer " + retrieveToken())
                .header("If-Match", hardwareETag)
                .when()
                .put("/api/hardware/id/" + hardwareId)
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("archive", equalTo(testHardware.isArchive()))
                .body("price", equalTo(testHardware.getPrice()))
                .body("hardwareType.condition", equalTo(testHardware.getHardwareType().getCondition().toString()))
                .body("hardwareType.type", equalTo(testHardware.getHardwareType().getType()));
    }

    @Test
    void updateHardwareFieldHardwareTypeTest() {
        HardwareType newComputer = Computer.builder()
                .condition(Condition.AVERAGE)
                .type("COMPUTER")
                .build();
        testHardware.setHardwareType(newComputer);
        given()
                .contentType(ContentType.JSON)
                .body(testHardware)
                .header("Authorization", "Bearer " + retrieveToken())
                .header("If-Match", hardwareETag)
                .when()
                .put("/api/hardware/id/" + hardwareId)
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("archive", equalTo(testHardware.isArchive()))
                .body("price", equalTo(testHardware.getPrice()))
                .body("hardwareType.condition", equalTo(testHardware.getHardwareType().getCondition().toString()))
                .body("hardwareType.type", equalTo(testHardware.getHardwareType().getType()));
    }

    @Test
    void updateHardwareFieldArchiveTest() {
        testHardware.setArchive(true);
        given()
                .contentType(ContentType.JSON)
                .body(testHardware)
                .header("Authorization", "Bearer " + retrieveToken())
                .header("If-Match", hardwareETag)
                .when()
                .put("/api/hardware/id/" + hardwareId)
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("archive", equalTo(testHardware.isArchive()))
                .body("price", equalTo(testHardware.getPrice()))
                .body("hardwareType.condition", equalTo(testHardware.getHardwareType().getCondition().toString()))
                .body("hardwareType.type", equalTo(testHardware.getHardwareType().getType()));
    }

    @Test
    void updateHardwareFieldPriceTest() {
        testHardware.setPrice(200);
        given()
                .contentType(ContentType.JSON)
                .body(testHardware)
                .header("Authorization", "Bearer " + retrieveToken())
                .header("If-Match", hardwareETag)
                .when()
                .put("/api/hardware/id/" + hardwareId)
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("archive", equalTo(testHardware.isArchive()))
                .body("price", equalTo(testHardware.getPrice()))
                .body("hardwareType.condition", equalTo(testHardware.getHardwareType().getCondition().toString()))
                .body("hardwareType.type", equalTo(testHardware.getHardwareType().getType()));
    }

    @Test
    void updateHardwareMissingIdTest() {
        given()
                .contentType(ContentType.JSON)
                .body(testHardware)
                .header("Authorization", "Bearer " + retrieveToken())
                .header("If-Match", hardwareETag)
                .when()
                .put("/api/hardware/id/")
                .then()
                .assertThat()
                .statusCode(404);
    }

    @Test
    void updateHardwareInvalidIfMatchHeader() {
        given()
                .contentType(ContentType.JSON)
                .body(testHardware)
                .header("Authorization", "Bearer " + retrieveToken())
                .header("If-Match", "INVALID_ETAG")
                .when()
                .put("/api/hardware/id/" + hardwareId)
                .then()
                .assertThat()
                .statusCode(412);
    }

    @Test
    void updateHardwareMissingIfMatchHeader() {
        given()
                .contentType(ContentType.JSON)
                .body(testHardware)
                .header("Authorization", "Bearer " + retrieveToken())
                .when()
                .put("/api/hardware/id/" + hardwareId)
                .then()
                .assertThat()
                .statusCode(400);
    }

    @Test
    void deleteHardwarePositiveTest() {
        given()
                .header("Authorization", "Bearer " + retrieveToken())
                .when()
                .delete("/api/hardware/id/" + hardwareId)
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    void deleteHardwareMissingIdTest() {
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + retrieveToken())
                .when()
                .delete("/api/hardware/id/")
                .then()
                .assertThat()
                .statusCode(404);
    }

    @Test
    void deleteHardwareIdDoesNotExistTest() {
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + retrieveToken())
                .when()
                .delete("/api/hardware/id/")
                .then()
                .assertThat()
                .statusCode(404);
    }
}
