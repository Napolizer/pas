package org.pl.gateway.module.controllers;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.microshed.testing.SharedContainerConfig;
import org.microshed.testing.jupiter.MicroShedTest;
import org.pl.gateway.module.model.AddressRest;
import org.pl.gateway.module.model.BasicRest;
import org.pl.gateway.module.model.ClientAccessTypeRest;
import org.pl.gateway.module.model.ClientRest;

import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThan;

@MicroShedTest
@SharedContainerConfig(AppContainerConfig.class)
public class ClientControllerIT {
//    String userId;
//    String userPassword;
//    String adminId;
//    String adminPassword;
//
//    public ClientControllerIT() {
//        adminPassword = "password";
//        userPassword = "password";
//        adminId = given()
//                .header("Authorization", "Bearer " + retrieveToken())
//                .when()
//                .get("/api/client/username/admin")
//                .then()
//                .extract()
//                .path("id");
//        userId = given()
//                .header("Authorization", "Bearer " + retrieveUserToken())
//                .when()
//                .get("/api/client/username/user")
//                .then()
//                .extract()
//                .path("id");
//    }
//
//    private String retrieveToken() {
//        Map<String, Object> credentials = new HashMap<>();
//        credentials.put("username", "admin");
//        credentials.put("password", adminPassword);
//        return given()
//                .contentType(ContentType.JSON)
//                .body(credentials)
//                .when()
//                .post("/api/client/login")
//                .then()
//                .assertThat()
//                .statusCode(200)
//                .contentType(ContentType.JSON)
//                .body("token", is(instanceOf(String.class)))
//                .extract()
//                .path("token");
//    }
//
//    private String retrieveUserToken() {
//        Map<String, Object> credentials = new HashMap<>();
//        credentials.put("username", "user");
//        credentials.put("password", userPassword);
//        return given()
//                .contentType(ContentType.JSON)
//                .body(credentials)
//                .when()
//                .post("/api/client/login")
//                .then()
//                .assertThat()
//                .statusCode(200)
//                .contentType(ContentType.JSON)
//                .body("token", is(instanceOf(String.class)))
//                .extract()
//                .path("token");
//    }
//
//    @Nested
//    class CreateClient {
//        ClientRest testClient = ClientRest.builder()
//                .address(AddressRest
//                        .builder()
//                        .city("Lodz")
//                        .number("123")
//                        .street("Narutowicza")
//                        .build())
//                .archive(false)
//                .balance(100.0)
//                .clientAccessType(ClientAccessTypeRest.USERS)
//                .clientType(new BasicRest())
//                .firstName("Janusz")
//                .lastName("Kowalski")
//                .phoneNumber("123456789")
//                .username("januszkowalski")
//                .password("januszek")
//                .build();
//
//        @Test
//        public void CreateClientPositiveTest() {
//            given()
//                    .contentType(ContentType.JSON)
//                    .body(testClient)
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .post("/api/client")
//                    .then()
//                    .assertThat()
//                    .statusCode(201)
//                    .body("archive", is(false))
//                    .body("balance", is(100.0F))
//                    .body("clientAccessType", is("USERS"))
//                    .body("clientType.type", is("BASIC"))
//                    .body("firstName", is("Janusz"))
//                    .body("lastName", is("Kowalski"))
//                    .body("phoneNumber", is("123456789"))
//                    .body("username", is("januszkowalski"));
//        }
//        @Test
//        public void createClientMissingBodyTest() {
//            given()
//                    .contentType(ContentType.JSON)
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .post("/api/client")
//                    .then()
//                    .assertThat()
//                    .statusCode(400);
//        }
//
//        @Test
//        public void createClientMissingUsernameTest() {
//            testClient.setUsername(null);
//            given()
//                    .contentType(ContentType.JSON)
//                    .body(testClient)
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .post("/api/client")
//                    .then()
//                    .assertThat()
//                    .statusCode(400);
//        }
//
//        @Test
//        public void createClientMissingPasswordTest() {
//            testClient.setPassword(null);
//            given()
//                    .contentType(ContentType.JSON)
//                    .body(testClient)
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .post("/api/client")
//                    .then()
//                    .assertThat()
//                    .statusCode(400);
//        }
//
//        @Test
//        public void createClientMissingFirstNameTest() {
//            testClient.setFirstName(null);
//            given()
//                    .contentType(ContentType.JSON)
//                    .body(testClient)
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .post("/api/client")
//                    .then()
//                    .assertThat()
//                    .statusCode(400);
//        }
//
//        @Test
//        public void createClientMissingLastNameTest() {
//            testClient.setLastName(null);
//            given()
//                    .contentType(ContentType.JSON)
//                    .body(testClient)
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .post("/api/client")
//                    .then()
//                    .assertThat()
//                    .statusCode(400);
//        }
//
//        @Test
//        public void createClientMissingPhoneNumberTest() {
//            testClient.setPhoneNumber(null);
//            given()
//                    .contentType(ContentType.JSON)
//                    .body(testClient)
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .post("/api/client")
//                    .then()
//                    .assertThat()
//                    .statusCode(400);
//        }
//
//        @Test
//        public void createClientMissingAddressTest() {
//            testClient.setAddress(null);
//            given()
//                    .contentType(ContentType.JSON)
//                    .body(testClient)
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .post("/api/client")
//                    .then()
//                    .assertThat()
//                    .statusCode(400);
//        }
//
//        @Test
//        public void createClientMissingCityTest() {
//            testClient.getAddress().setCity(null);
//            given()
//                    .contentType(ContentType.JSON)
//                    .body(testClient)
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .post("/api/client")
//                    .then()
//                    .assertThat()
//                    .statusCode(400);
//        }
//
//        @Test
//        public void createClientMissingStreetTest() {
//            testClient.getAddress().setStreet(null);
//            given()
//                    .contentType(ContentType.JSON)
//                    .body(testClient)
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .post("/api/client")
//                    .then()
//                    .assertThat()
//                    .statusCode(400);
//        }
//
//        @Test
//        public void createClientMissingNumberTest() {
//            testClient.getAddress().setNumber(null);
//            given()
//                    .contentType(ContentType.JSON)
//                    .body(testClient)
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .post("/api/client")
//                    .then()
//                    .assertThat()
//                    .statusCode(400);
//        }
//
//        @Test
//        public void createClientMissingClientTypeTest() {
//            testClient.setClientType(null);
//            given()
//                    .contentType(ContentType.JSON)
//                    .body(testClient)
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .post("/api/client")
//                    .then()
//                    .assertThat()
//                    .statusCode(400);
//        }
//
//        @Test
//        public void createClientMissingClientAccessTypeTest() {
//            testClient.setClientAccessType(null);
//            given()
//                    .contentType(ContentType.JSON)
//                    .body(testClient)
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .post("/api/client")
//                    .then()
//                    .assertThat()
//                    .statusCode(400);
//        }
//
//        @Test
//        public void createClientMissingBalanceTest() {
//            testClient.setBalance(null);
//            given()
//                    .contentType(ContentType.JSON)
//                    .body(testClient)
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .post("/api/client")
//                    .then()
//                    .assertThat()
//                    .statusCode(400);
//        }
//    }
//
//    @Nested
//    class GetClient {
//        @Test
//        void getClientPositiveTest() {
//            given()
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .get("/api/client/id/" + adminId)
//                    .then()
//                    .assertThat()
//                    .statusCode(200)
//                    .contentType(ContentType.JSON)
//                    .body("id", is(adminId))
//                    .body("archive", is(false))
//                    .body("balance", is(0.0F))
//                    .body("clientAccessType", is("ADMINISTRATORS"))
//                    .body("clientType.type", is("PREMIUM"))
//                    .body("firstName", is("Admin"))
//                    .body("lastName", is("Admin"))
//                    .body("phoneNumber", is("123456789"))
//                    .body("username", is("admin"))
//                    .body("password", is(equalTo(null)));
//        }
//
//        @Test
//        void getClientMissingIdTest() {
//            given()
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .get("/api/client/id/")
//                    .then()
//                    .assertThat()
//                    .statusCode(404);
//        }
//
//        @Test
//        void getClientNegativeIdTest() {
//            given()
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .get("/api/client/id/-1")
//                    .then()
//                    .assertThat()
//                    .statusCode(400);
//        }
//
//        @Test
//        void getClientWrongIdTest() {
//            given()
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .get("/api/client/id/abc")
//                    .then()
//                    .assertThat()
//                    .statusCode(400);
//        }
//    }
//
//    @Nested
//    class GetAllClients {
//        @Test
//        void getAllClientsPositiveTest() {
//            given()
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .get("/api/clients")
//                    .then()
//                    .assertThat()
//                    .statusCode(200)
//                    .contentType(ContentType.JSON)
//                    .body("size()", is(greaterThan(2)));
//        }
//    }
//
//    @Nested
//    class GetClientByUsername {
//        @Test
//        void getClientByUsernamePositiveTest() {
//            given()
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .get("/api/client/username/admin")
//                    .then()
//                    .assertThat()
//                    .statusCode(200)
//                    .contentType(ContentType.JSON)
//                    .body("id", is(adminId))
//                    .body("archive", is(false))
//                    .body("balance", is(0.0F))
//                    .body("clientAccessType", is("ADMINISTRATORS"))
//                    .body("clientType.type", is("PREMIUM"))
//                    .body("firstName", is("Admin"))
//                    .body("lastName", is("Admin"))
//                    .body("phoneNumber", is("123456789"))
//                    .body("username", is("admin"))
//                    .body("password", is(equalTo(null)));
//        }
//
//        @Test
//        void getClientByUsernameMissingUsernameTest() {
//            given()
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .get("/api/client/username/")
//                    .then()
//                    .assertThat()
//                    .statusCode(404);
//        }
//
//        @Test
//        void getClientByUsernameWrongUsernameTest() {
//            given()
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .get("/api/client/username/abc")
//                    .then()
//                    .assertThat()
//                    .statusCode(404);
//        }
//    }
//
//    @Nested
//    class GetPastRepairs {
//        @Test
//        void getPastRepairsPositiveTest() {
//            given()
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .get("/api/client/id/" + adminId + "/repair/past")
//                    .then()
//                    .assertThat()
//                    .statusCode(200)
//                    .contentType(ContentType.JSON)
//                    .body("size()", is(0));
//        }
//
//        @Test
//        void getPastRepairsMissingIdTest() {
//            given()
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .get("/api/client/id//repair/past")
//                    .then()
//                    .assertThat()
//                    .statusCode(404);
//        }
//
//        @Test
//        void getPastRepairsNegativeIdTest() {
//            given()
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .get("/api/client/id/-1/repair/past")
//                    .then()
//                    .assertThat()
//                    .statusCode(400);
//        }
//
//        @Test
//        void getPastRepairsWrongIdTest() {
//            given()
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .get("/api/client/id/abc/repair/past")
//                    .then()
//                    .assertThat()
//                    .statusCode(400);
//        }
//
//        @Test
//        void getPastRepairsWrongUrlTest() {
//            given()
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .get("/api/client/id/" + adminId + "/repair/past/abc")
//                    .then()
//                    .assertThat()
//                    .statusCode(404);
//        }
//    }
//
//    @Nested
//    class GetPresentRepairsTest {
//        @Test
//        void getPresentRepairsPositiveTest() {
//            given()
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .get("/api/client/id/" + adminId + "/repair/present")
//                    .then()
//                    .assertThat()
//                    .statusCode(200)
//                    .contentType(ContentType.JSON)
//                    .body("size()", is(0));
//        }
//
//        @Test
//        void getPresentRepairsMissingIdTest() {
//            given()
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .get("/api/client/id//repair/present")
//                    .then()
//                    .assertThat()
//                    .statusCode(404);
//        }
//
//        @Test
//        void getPresentRepairsNegativeIdTest() {
//            given()
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .get("/api/client/id/-1/repair/present")
//                    .then()
//                    .assertThat()
//                    .statusCode(400);
//        }
//
//        @Test
//        void getPresentRepairsWrongIdTest() {
//            given()
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .get("/api/client/id/abc/repair/present")
//                    .then()
//                    .assertThat()
//                    .statusCode(400);
//        }
//
//        @Test
//        void getPresentRepairsWrongUrlTest() {
//            given()
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .get("/api/client/id/" + adminId + "/repair/present/abc")
//                    .then()
//                    .assertThat()
//                    .statusCode(404);
//        }
//    }
//
//    @Nested
//    class DeactivateClient {
//        @Test
//        void deactivateClientPositiveTest() {
//            given()
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .body(HttpRequest.BodyPublishers.noBody())
//                    .when()
//                    .put("/api/client/id/" + userId + "/deactivate")
//                    .then()
//                    .assertThat()
//                    .statusCode(200);
//
//            given()
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .get("/api/client/id/" + userId)
//                    .then()
//                    .assertThat()
//                    .statusCode(200)
//                    .contentType(ContentType.JSON)
//                    .body("archive", is(true));
//
//            given()
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .body(HttpRequest.BodyPublishers.noBody())
//                    .when()
//                    .put("/api/client/id/" + userId + "/activate")
//                    .then()
//                    .assertThat()
//                    .statusCode(200);
//        }
//
//        @Test
//        void deactivateClientMissingIdTest() {
//            given()
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .put("/api/client/id//deactivate")
//                    .then()
//                    .assertThat()
//                    .statusCode(404);
//        }
//
//        @Test
//        void deactivateClientNegativeIdTest() {
//            given()
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .put("/api/client/id/-1/deactivate")
//                    .then()
//                    .assertThat()
//                    .statusCode(400);
//        }
//
//        @Test
//        void deactivateClientWrongIdTest() {
//            given()
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .put("/api/client/id/abc/deactivate")
//                    .then()
//                    .assertThat()
//                    .statusCode(400);
//        }
//
//        @Test
//        void deactivateClientWrongUrlTest() {
//            given()
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .put("/api/client/id/" + adminId + "/deactivate/abc")
//                    .then()
//                    .assertThat()
//                    .statusCode(404);
//        }
//
//        @Test
//        void deactivateClientAlreadyDeactivatedTest() {
//            given()
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .put("/api/client/id/" + userId + "/deactivate")
//                    .then()
//                    .assertThat()
//                    .statusCode(200);
//
//            given()
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .put("/api/client/id/" + userId + "/deactivate")
//                    .then()
//                    .assertThat()
//                    .statusCode(400);
//
//            given()
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .body(HttpRequest.BodyPublishers.noBody())
//                    .when()
//                    .put("/api/client/id/" + userId + "/activate")
//                    .then()
//                    .assertThat()
//                    .statusCode(200);
//        }
//    }
//
//    @Nested
//    class ActivateClient {
//        @Test
//        void activateClientPositiveTest() {
//            given()
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .put("/api/client/id/" + userId + "/deactivate")
//                    .then()
//                    .assertThat()
//                    .statusCode(200);
//
//            given()
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .put("/api/client/id/" + userId + "/activate")
//                    .then()
//                    .assertThat()
//                    .statusCode(200);
//
//            given()
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .get("/api/client/id/" + userId)
//                    .then()
//                    .assertThat()
//                    .statusCode(200)
//                    .contentType(ContentType.JSON)
//                    .body("archive", is(false));
//        }
//
//        @Test
//        void activateClientMissingIdTest() {
//            given()
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .put("/api/client/id//activate")
//                    .then()
//                    .assertThat()
//                    .statusCode(404);
//        }
//
//        @Test
//        void activateClientNegativeIdTest() {
//            given()
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .put("/api/client/id/-1/activate")
//                    .then()
//                    .assertThat()
//                    .statusCode(400);
//        }
//
//        @Test
//        void activateClientWrongIdTest() {
//            given()
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .put("/api/client/id/abc/activate")
//                    .then()
//                    .assertThat()
//                    .statusCode(400);
//        }
//
//        @Test
//        void activateClientWrongUrlTest() {
//            given()
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .put("/api/client/id/" + adminId + "/activate/abc")
//                    .then()
//                    .assertThat()
//                    .statusCode(404);
//        }
//
//        @Test
//        void activateClientAlreadyActivatedTest() {
//            given()
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .when()
//                    .put("/api/client/id/" + userId + "/activate")
//                    .then()
//                    .assertThat()
//                    .statusCode(400);
//        }
//    }
//
//    @Nested
//    class Login {
//        @Test
//        public void loginPositiveTest() {
//            Map<String, Object> credentials = new HashMap<>();
//            credentials.put("username", "admin");
//            credentials.put("password", "password");
//            given()
//                    .contentType(ContentType.JSON)
//                    .body(credentials)
//                    .when()
//                    .post("/api/client/login")
//                    .then()
//                    .assertThat()
//                    .statusCode(200)
//                    .contentType(ContentType.JSON)
//                    .body("token", is(instanceOf(String.class)));
//        }
//
//        @Test
//        public void loginMissingUsernameTest() {
//            Map<String, Object> credentials = new HashMap<>();
//            credentials.put("password", "password");
//            given()
//                    .contentType(ContentType.JSON)
//                    .body(credentials)
//                    .when()
//                    .post("/api/client/login")
//                    .then()
//                    .assertThat()
//                    .statusCode(400);
//        }
//
//        @Test
//        public void loginMissingPasswordTest() {
//            Map<String, Object> credentials = new HashMap<>();
//            credentials.put("username", "admin");
//            given()
//                    .contentType(ContentType.JSON)
//                    .body(credentials)
//                    .when()
//                    .post("/api/client/login")
//                    .then()
//                    .assertThat()
//                    .statusCode(400);
//        }
//
//        @Test
//        public void loginWrongUsernameTest() {
//            Map<String, Object> credentials = new HashMap<>();
//            credentials.put("username", "abc");
//            credentials.put("password", "password");
//            given()
//                    .contentType(ContentType.JSON)
//                    .body(credentials)
//                    .when()
//                    .post("/api/client/login")
//                    .then()
//                    .assertThat()
//                    .statusCode(404);
//        }
//
//        @Test
//        public void loginWrongPasswordTest() {
//            Map<String, Object> credentials = new HashMap<>();
//            credentials.put("username", "admin");
//            credentials.put("password", "abc");
//            given()
//                    .contentType(ContentType.JSON)
//                    .body(credentials)
//                    .when()
//                    .post("/api/client/login")
//                    .then()
//                    .assertThat()
//                    .statusCode(401);
//        }
//
//        @Test
//        public void loginWrongUsernameAndPasswordTest() {
//            Map<String, Object> credentials = new HashMap<>();
//            credentials.put("username", "abc");
//            credentials.put("password", "abc");
//            given()
//                    .contentType(ContentType.JSON)
//                    .body(credentials)
//                    .when()
//                    .post("/api/client/login")
//                    .then()
//                    .assertThat()
//                    .statusCode(404);
//        }
//
//        @Test
//        public void loginWrongUsernameAndPasswordEmptyTest() {
//            Map<String, Object> credentials = new HashMap<>();
//            credentials.put("username", "");
//            credentials.put("password", "");
//            given()
//                    .contentType(ContentType.JSON)
//                    .body(credentials)
//                    .when()
//                    .post("/api/client/login")
//                    .then()
//                    .assertThat()
//                    .statusCode(400);
//        }
//
//        @Test
//        public void loginWrongUsernameAndPasswordNullTest() {
//            Map<String, Object> credentials = new HashMap<>();
//            credentials.put("username", null);
//            credentials.put("password", null);
//            given()
//                    .contentType(ContentType.JSON)
//                    .body(credentials)
//                    .when()
//                    .post("/api/client/login")
//                    .then()
//                    .assertThat()
//                    .statusCode(400);
//        }
//
//        @Test
//        public void loginWrongUsernameAndPasswordMissingTest() {
//            Map<String, Object> credentials = new HashMap<>();
//            given()
//                    .contentType(ContentType.JSON)
//                    .body(credentials)
//                    .when()
//                    .post("/api/client/login")
//                    .then()
//                    .assertThat()
//                    .statusCode(400);
//        }
//    }
//
//    @Nested
//    class ChangePassword {
//        @Test
//        public void changePasswordPositiveTest() {
//            Map<String, Object> password = new HashMap<>();
//            password.put("newPassword", "newPassword");
//            given()
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .contentType(ContentType.JSON)
//                    .body(password)
//                    .when()
//                    .put("/api/client/id/" + adminId + "/change_password")
//                    .then()
//                    .assertThat()
//                    .statusCode(200);
//            // Should not be able to log in using old password
//            Map<String, Object> credentials = new HashMap<>();
//            credentials.put("username", "admin");
//            credentials.put("password", "password");
//            given()
//                    .contentType(ContentType.JSON)
//                    .body(credentials)
//                    .when()
//                    .post("/api/client/login")
//                    .then()
//                    .assertThat()
//                    .statusCode(401);
//
//            adminPassword = "newPassword";
//            password.put("newPassword", "password");
//            given()
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .contentType(ContentType.JSON)
//                    .body(password)
//                    .when()
//                    .put("/api/client/id/" + adminId + "/change_password")
//                    .then()
//                    .assertThat()
//                    .statusCode(200);
//        }
//
//        @Test
//        public void changePasswordEmptyBody() {
//            Map<String, Object> body = new HashMap<>();
//            given()
//                    .header("Authorization", "Bearer " + retrieveToken())
//                    .contentType(ContentType.JSON)
//                    .body(body)
//                    .when()
//                    .put("/api/client/id/" + adminId + "/change_password")
//                    .then()
//                    .assertThat()
//                    .statusCode(400);
//        }
//    }
}
