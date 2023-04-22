package org.pl.user.module.controllers;

import org.junit.jupiter.api.Test;
import org.microshed.testing.SharedContainerConfig;
import org.microshed.testing.jupiter.MicroShedTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


@MicroShedTest
@SharedContainerConfig(AppContainerConfig.class)
class HealthControllerIT {
    @Test
    void getAllHardwaresTest() {
        given()
                .when()
                .get("/api/health")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType("text/plain")
                .body(equalTo("OK"));
    }
}
