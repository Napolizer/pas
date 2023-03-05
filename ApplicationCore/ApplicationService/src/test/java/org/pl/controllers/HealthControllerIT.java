package org.pl.controllers;

import org.junit.jupiter.api.Test;
import org.microshed.testing.jupiter.MicroShedTest;
import org.microshed.testing.testcontainers.ApplicationContainer;
import org.testcontainers.junit.jupiter.Container;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;


@MicroShedTest
public class HealthControllerIT {
    @Container
    public static ApplicationContainer container = new ApplicationContainer()
            .withAppContextRoot("/ApplicationService-1.0-SNAPSHOT")
            .withReadinessPath("/ApplicationService-1.0-SNAPSHOT/api/health");


    @Test
    void getAllHardwaresTest() {
        given()
                .when().
                get("/api/health")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType("text/plain")
                .body(equalTo("OK"));
    }
}
