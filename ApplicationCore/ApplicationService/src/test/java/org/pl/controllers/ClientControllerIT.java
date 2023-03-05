package org.pl.controllers;

import org.junit.Test;
import org.microshed.testing.jupiter.MicroShedTest;
import org.microshed.testing.testcontainers.ApplicationContainer;
import org.testcontainers.junit.jupiter.Container;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@MicroShedTest
public class ClientControllerIT {
    @Container
    public static ApplicationContainer container = new ApplicationContainer()
            .withAppContextRoot("/ApplicationService-1.0-SNAPSHOT")
            .withReadinessPath("/ApplicationService-1.0-SNAPSHOT/api/health");

    @Test
    public void loginPositiveTest() {
        given()
                .when()
                .header("Content-Type", "application/json")
                .body("{\"username\":\"admin\",\"password\":\"password\"}")
                .get("/api/client/login")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType("application/json")
                .body("token", is(instanceOf(String.class)));
    }
}
