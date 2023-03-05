package org.pl.controllers;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
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
public class HardwareControllerIT {
    static String token = "";

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

//    @BeforeAll
//    static void login() {
//        Response response = given()
//                .when()
//                .contentType(ContentType.JSON)
//                .header("Content-Type", "application/json")
//                .body("{\"username\":\"admin\",\"password\":\"password\"}")
//                .get("/api/client/login")
//                .then()
//                .extract()
//                .response();
//        token = response.path("token").toString();
//    }
    @Test
    void CreateHardwareMissingBodyTest() {
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + retrieveToken())
                .when()
                .post("/api/hardware")
                .then()
                .assertThat()
                .statusCode(400);
    }
}
