package org.pl.gateway.module.controllers;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.microshed.testing.SharedContainerConfig;
import org.microshed.testing.jupiter.MicroShedTest;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

@MicroShedTest
@SharedContainerConfig(AppContainerConfig.class)
public class ClientTypeControllerIT {
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

    @Test
    void getClientTypeMissingIdNegativeTest() {
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + retrieveToken())
                .when()
                .get("/api/client-type/id/")
                .then()
                .assertThat()
                .statusCode(404);
    }

    @Test
    void getClientTypeNonExistingIdNegativeTest() {
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + retrieveToken())
                .when()
                .get("/api/client-type/id/0e444d4e-8dc9-47a6-97cb-b4e47f937923")
                .then()
                .assertThat()
                .statusCode(404);
    }
}