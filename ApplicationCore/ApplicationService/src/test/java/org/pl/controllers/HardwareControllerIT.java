package org.pl.controllers;

import org.junit.jupiter.api.Test;
import org.microshed.testing.SharedContainerConfig;
import org.microshed.testing.jupiter.MicroShedTest;

import static io.restassured.RestAssured.given;

@MicroShedTest
@SharedContainerConfig(AppContainerConfig.class)
public class HardwareControllerIT {
    @Test
    void CreateHardwareEmptyBodyTest() {
        given()
                .body("")
                .when()
                .post("/api/hardware")
                .then()
                .assertThat()
                .statusCode(400);
    }
}
