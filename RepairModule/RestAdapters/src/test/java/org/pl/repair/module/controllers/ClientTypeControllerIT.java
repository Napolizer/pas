package org.pl.repair.module.controllers;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.microshed.testing.SharedContainerConfig;
import org.microshed.testing.jupiter.MicroShedTest;
import org.pl.repair.module.factories.TokenFactory;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

@MicroShedTest
@SharedContainerConfig(AppContainerConfig.class)
class ClientTypeControllerIT {
    private static final TokenFactory tokenFactory = new TokenFactory();

    private static String retrieveToken() {
        return tokenFactory.generateAdminToken("admin");
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