package org.pl.controllers;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Nested;
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
public class ClientControllerIT {
    static Client admin;

    @BeforeClass
    public static void setUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        admin = given()
                .header("Authorization", "Bearer " + retrieveToken())
                .when()
                .get("/api/client/username/admin")
                .then()
                .extract()
                .as(Client.class);
    }

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

    @Nested
    class CreateClient {
        Client testClient = Client.builder()
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

        @Test
        public void CreateClientPositiveTest() {
            given()
                    .contentType(ContentType.JSON)
                    .body(testClient)
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .post("/api/client")
                    .then()
                    .assertThat()
                    .statusCode(201)
                    .body("archive", is(false))
                    .body("balance", is(100.0F))
                    .body("clientAccessType", is("USERS"))
                    .body("clientType.type", is("BASIC"))
                    .body("firstName", is("Janusz"))
                    .body("lastName", is("Kowalski"))
                    .body("phoneNumber", is("123456789"))
                    .body("username", is("januszkowalski"));
        }
        @Test
        public void createClientMissingBodyTest() {
            given()
                    .contentType(ContentType.JSON)
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .post("/api/client")
                    .then()
                    .assertThat()
                    .statusCode(400);
        }

        @Test
        public void createClientMissingUsernameTest() {
            testClient.setUsername(null);
            given()
                    .contentType(ContentType.JSON)
                    .body(testClient)
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .post("/api/client")
                    .then()
                    .assertThat()
                    .statusCode(400);
        }

        @Test
        public void createClientMissingPasswordTest() {
            testClient.setPassword(null);
            given()
                    .contentType(ContentType.JSON)
                    .body(testClient)
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .post("/api/client")
                    .then()
                    .assertThat()
                    .statusCode(400);
        }

        @Test
        public void createClientMissingFirstNameTest() {
            testClient.setFirstName(null);
            given()
                    .contentType(ContentType.JSON)
                    .body(testClient)
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .post("/api/client")
                    .then()
                    .assertThat()
                    .statusCode(400);
        }

        @Test
        public void createClientMissingLastNameTest() {
            testClient.setLastName(null);
            given()
                    .contentType(ContentType.JSON)
                    .body(testClient)
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .post("/api/client")
                    .then()
                    .assertThat()
                    .statusCode(400);
        }

        @Test
        public void createClientMissingPhoneNumberTest() {
            testClient.setPhoneNumber(null);
            given()
                    .contentType(ContentType.JSON)
                    .body(testClient)
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .post("/api/client")
                    .then()
                    .assertThat()
                    .statusCode(400);
        }

        @Test
        public void createClientMissingAddressTest() {
            testClient.setAddress(null);
            given()
                    .contentType(ContentType.JSON)
                    .body(testClient)
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .post("/api/client")
                    .then()
                    .assertThat()
                    .statusCode(400);
        }

        @Test
        public void createClientMissingCityTest() {
            testClient.getAddress().setCity(null);
            given()
                    .contentType(ContentType.JSON)
                    .body(testClient)
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .post("/api/client")
                    .then()
                    .assertThat()
                    .statusCode(400);
        }

        @Test
        public void createClientMissingStreetTest() {
            testClient.getAddress().setStreet(null);
            given()
                    .contentType(ContentType.JSON)
                    .body(testClient)
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .post("/api/client")
                    .then()
                    .assertThat()
                    .statusCode(400);
        }

        @Test
        public void createClientMissingNumberTest() {
            testClient.getAddress().setNumber(null);
            given()
                    .contentType(ContentType.JSON)
                    .body(testClient)
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .post("/api/client")
                    .then()
                    .assertThat()
                    .statusCode(400);
        }

        @Test
        public void createClientMissingClientTypeTest() {
            testClient.setClientType(null);
            given()
                    .contentType(ContentType.JSON)
                    .body(testClient)
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .post("/api/client")
                    .then()
                    .assertThat()
                    .statusCode(400);
        }

        @Test
        public void createClientMissingClientAccessTypeTest() {
            testClient.setClientAccessType(null);
            given()
                    .contentType(ContentType.JSON)
                    .body(testClient)
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .post("/api/client")
                    .then()
                    .assertThat()
                    .statusCode(400);
        }

        @Test
        public void createClientMissingBalanceTest() {
            testClient.setBalance(null);
            given()
                    .contentType(ContentType.JSON)
                    .body(testClient)
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .post("/api/client")
                    .then()
                    .assertThat()
                    .statusCode(400);
        }
    }

    @Test
    public void getClientByUsername() {
        given()
                .when()
                .header("Authorization", "Bearer " + retrieveToken())
                .get("/api/client/username/admin")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("username", is("admin"));
    }

    @Test
    public void loginPositiveTest() {
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("username", "admin");
        credentials.put("password", "password");
        given()
                .contentType(ContentType.JSON)
                .body(credentials)
                .when()
                .post("/api/client/login")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("token", is(instanceOf(String.class)));
    }
}
