package org.pl.user.module.controllers;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.microshed.testing.SharedContainerConfig;
import org.microshed.testing.jupiter.MicroShedTest;
import org.pl.user.module.model.AddressRest;
import org.pl.user.module.model.UserAccessTypeRest;
import org.pl.user.module.model.UserRest;

import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@MicroShedTest
@SharedContainerConfig(AppContainerConfig.class)
public class UserControllerIT {
    String userId;
    String userPassword;
    String adminId;
    String adminPassword;

    public UserControllerIT() {
        adminPassword = "password";
        userPassword = "password";
        adminId = given()
                .header("Authorization", "Bearer " + retrieveToken())
                .when()
                .get("/api/user/username/admin")
                .then()
                .extract()
                .path("id");
        userId = given()
                .header("Authorization", "Bearer " + retrieveUserToken())
                .when()
                .get("/api/user/username/user")
                .then()
                .extract()
                .path("id");
    }

    private String retrieveToken() {
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("username", "admin");
        credentials.put("password", adminPassword);
        return given()
                .contentType(ContentType.JSON)
                .body(credentials)
                .when()
                .post("/api/user/login")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("token", is(instanceOf(String.class)))
                .extract()
                .path("token");
    }

    private String retrieveUserToken() {
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("username", "user");
        credentials.put("password", userPassword);
        return given()
                .contentType(ContentType.JSON)
                .body(credentials)
                .when()
                .post("/api/user/login")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("token", is(instanceOf(String.class)))
                .extract()
                .path("token");
    }

    @Nested
    class CreateUser {
        UserRest testUser = UserRest.builder()
                .address(AddressRest
                        .builder()
                        .city("Lodz")
                        .number("123")
                        .street("Narutowicza")
                        .build())
                .archive(false)
                .clientAccessType(UserAccessTypeRest.USERS)
                .firstName("Janusz")
                .lastName("Kowalski")
                .phoneNumber("123456789")
                .username("januszkowalski")
                .password("januszek")
                .build();

        @Test
        void createUserPositiveTest() {
            given()
                    .contentType(ContentType.JSON)
                    .header("Authorization", "Bearer " + retrieveToken())
                    .body(testUser)
                    .when()
                    .post("/api/user")
                    .then()
                    .assertThat()
                    .statusCode(201)
                    .body("archive", is(false))
                    .body("clientAccessType", is("USERS"))
                    .body("firstName", is("Janusz"))
                    .body("lastName", is("Kowalski"))
                    .body("phoneNumber", is("123456789"))
                    .body("username", is("januszkowalski"));
        }

        @Test
        void createUserMissingBodyTest() {
            given()
                    .contentType(ContentType.JSON)
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .post("/api/user")
                    .then()
                    .assertThat()
                    .statusCode(400);
        }

        @Test
        void createUserMissingUsernameTest() {
            testUser.setUsername(null);
            given()
                    .contentType(ContentType.JSON)
                    .body(testUser)
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .post("/api/user")
                    .then()
                    .assertThat()
                    .statusCode(400);
        }

        @Test
        void createUserMissingPasswordTest() {
            testUser.setPassword(null);
            given()
                    .contentType(ContentType.JSON)
                    .body(testUser)
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .post("/api/user")
                    .then()
                    .assertThat()
                    .statusCode(400);
        }

        @Test
        void createUserMissingFirstNameTest() {
            testUser.setFirstName(null);
            given()
                    .contentType(ContentType.JSON)
                    .body(testUser)
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .post("/api/user")
                    .then()
                    .assertThat()
                    .statusCode(400);
        }

        @Test
        void createUserMissingLastNameTest() {
            testUser.setLastName(null);
            given()
                    .contentType(ContentType.JSON)
                    .body(testUser)
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .post("/api/user")
                    .then()
                    .assertThat()
                    .statusCode(400);
        }

        @Test
        void createUserMissingPhoneNumberTest() {
            testUser.setPhoneNumber(null);
            given()
                    .contentType(ContentType.JSON)
                    .body(testUser)
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .post("/api/user")
                    .then()
                    .assertThat()
                    .statusCode(400);
        }

        @Test
        void createUserMissingAddressTest() {
            testUser.setAddress(null);
            given()
                    .contentType(ContentType.JSON)
                    .body(testUser)
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .post("/api/user")
                    .then()
                    .assertThat()
                    .statusCode(400);
        }

        @Test
        void createUserMissingCityTest() {
            testUser.getAddress().setCity(null);
            given()
                    .contentType(ContentType.JSON)
                    .body(testUser)
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .post("/api/user")
                    .then()
                    .assertThat()
                    .statusCode(400);
        }

        @Test
        void createUserMissingStreetTest() {
            testUser.getAddress().setStreet(null);
            given()
                    .contentType(ContentType.JSON)
                    .body(testUser)
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .post("/api/user")
                    .then()
                    .assertThat()
                    .statusCode(400);
        }

        @Test
        void createUserMissingNumberTest() {
            testUser.getAddress().setNumber(null);
            given()
                    .contentType(ContentType.JSON)
                    .body(testUser)
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .post("/api/user")
                    .then()
                    .assertThat()
                    .statusCode(400);
        }

        @Test
        void createUserMissingClientAccessTypeTest() {
            testUser.setClientAccessType(null);
            given()
                    .contentType(ContentType.JSON)
                    .body(testUser)
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .post("/api/user")
                    .then()
                    .assertThat()
                    .statusCode(400);
        }
    }

    @Nested
    class GetUser {
        @Test
        void getUserPositiveTest() {
            given()
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .get("/api/user/id/" + adminId)
                    .then()
                    .assertThat()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .body("id", is(adminId))
                    .body("archive", is(false))
                    .body("clientAccessType", is("ADMINISTRATORS"))
                    .body("firstName", is("Admin"))
                    .body("lastName", is("Admin"))
                    .body("phoneNumber", is("123456789"))
                    .body("username", is("admin"))
                    .body("password", is(equalTo(null)));
        }

        @Test
        void getUserMissingIdTest() {
            given()
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .get("/api/user/id/")
                    .then()
                    .assertThat()
                    .statusCode(404);
        }

        @Test
        void getUserNegativeIdTest() {
            given()
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .get("/api/user/id/-1")
                    .then()
                    .assertThat()
                    .statusCode(400);
        }

        @Test
        void getUserWrongIdTest() {
            given()
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .get("/api/user/id/abc")
                    .then()
                    .assertThat()
                    .statusCode(400);
        }
    }

    @Nested
    class GetAllUsers {
        @Test
        void getAllUsersPositiveTest() {
            given()
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .get("/api/users")
                    .then()
                    .assertThat()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .body("size()", is(greaterThan(2)));
        }
    }

    @Nested
    class GetUserByUsername {
        @Test
        void getUserByUsernamePositiveTest() {
            given()
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .get("/api/user/username/admin")
                    .then()
                    .assertThat()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .body("id", is(adminId))
                    .body("archive", is(false))
                    .body("clientAccessType", is("ADMINISTRATORS"))
                    .body("firstName", is("Admin"))
                    .body("lastName", is("Admin"))
                    .body("phoneNumber", is("123456789"))
                    .body("username", is("admin"))
                    .body("password", is(equalTo(null)));
        }

        @Test
        void getUserByUsernameMissingUsernameTest() {
            given()
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .get("/api/user/username/")
                    .then()
                    .assertThat()
                    .statusCode(404);
        }

        @Test
        void getUserByUsernameWrongUsernameTest() {
            given()
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .get("/api/user/username/abc")
                    .then()
                    .assertThat()
                    .statusCode(404);
        }
    }

    @Nested
    class DeactivateUser {
        @Test
        void deactivateUserPositiveTest() {
            given()
                    .header("Authorization", "Bearer " + retrieveToken())
                    .body(HttpRequest.BodyPublishers.noBody())
                    .when()
                    .put("/api/user/id/" + userId + "/deactivate")
                    .then()
                    .assertThat()
                    .statusCode(200);

            given()
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .get("/api/user/id/" + userId)
                    .then()
                    .assertThat()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .body("archive", is(true));

            given()
                    .header("Authorization", "Bearer " + retrieveToken())
                    .body(HttpRequest.BodyPublishers.noBody())
                    .when()
                    .put("/api/user/id/" + userId + "/activate")
                    .then()
                    .assertThat()
                    .statusCode(200);
        }

        @Test
        void deactivateUserMissingIdTest() {
            given()
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .put("/api/user/id//deactivate")
                    .then()
                    .assertThat()
                    .statusCode(404);
        }

        @Test
        void deactivateUserNegativeIdTest() {
            given()
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .put("/api/user/id/-1/deactivate")
                    .then()
                    .assertThat()
                    .statusCode(400);
        }

        @Test
        void deactivateUserWrongIdTest() {
            given()
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .put("/api/user/id/abc/deactivate")
                    .then()
                    .assertThat()
                    .statusCode(400);
        }

        @Test
        void deactivateUserWrongUrlTest() {
            given()
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .put("/api/user/id/" + adminId + "/deactivate/abc")
                    .then()
                    .assertThat()
                    .statusCode(404);
        }

        @Test
        void deactivateUserAlreadyDeactivatedTest() {
            given()
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .put("/api/user/id/" + userId + "/deactivate")
                    .then()
                    .assertThat()
                    .statusCode(200);

            given()
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .put("/api/user/id/" + userId + "/deactivate")
                    .then()
                    .assertThat()
                    .statusCode(400);

            given()
                    .header("Authorization", "Bearer " + retrieveToken())
                    .body(HttpRequest.BodyPublishers.noBody())
                    .when()
                    .put("/api/user/id/" + userId + "/activate")
                    .then()
                    .assertThat()
                    .statusCode(200);
        }
    }

    @Nested
    class ActivateUser {
        @Test
        void activateUserPositiveTest() {
            given()
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .put("/api/user/id/" + userId + "/deactivate")
                    .then()
                    .assertThat()
                    .statusCode(200);

            given()
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .put("/api/user/id/" + userId + "/activate")
                    .then()
                    .assertThat()
                    .statusCode(200);

            given()
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .get("/api/user/id/" + userId)
                    .then()
                    .assertThat()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .body("archive", is(false));
        }

        @Test
        void activateUserMissingIdTest() {
            given()
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .put("/api/user/id//activate")
                    .then()
                    .assertThat()
                    .statusCode(404);
        }

        @Test
        void activateUserNegativeIdTest() {
            given()
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .put("/api/user/id/-1/activate")
                    .then()
                    .assertThat()
                    .statusCode(400);
        }

        @Test
        void activateUserWrongIdTest() {
            given()
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .put("/api/user/id/abc/activate")
                    .then()
                    .assertThat()
                    .statusCode(400);
        }

        @Test
        void activateUserWrongUrlTest() {
            given()
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .put("/api/user/id/" + adminId + "/activate/abc")
                    .then()
                    .assertThat()
                    .statusCode(404);
        }

        @Test
        void activateUserAlreadyActivatedTest() {
            given()
                    .header("Authorization", "Bearer " + retrieveToken())
                    .when()
                    .put("/api/user/id/" + userId + "/activate")
                    .then()
                    .assertThat()
                    .statusCode(400);
        }
    }

    @Nested
    class Login {
        @Test
        void loginPositiveTest() {
            Map<String, Object> credentials = new HashMap<>();
            credentials.put("username", "admin");
            credentials.put("password", "password");
            given()
                    .contentType(ContentType.JSON)
                    .body(credentials)
                    .when()
                    .post("/api/user/login")
                    .then()
                    .assertThat()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .body("token", is(instanceOf(String.class)));
        }

        @Test
        void loginMissingUsernameTest() {
            Map<String, Object> credentials = new HashMap<>();
            credentials.put("password", "password");
            given()
                    .contentType(ContentType.JSON)
                    .body(credentials)
                    .when()
                    .post("/api/user/login")
                    .then()
                    .assertThat()
                    .statusCode(400);
        }

        @Test
        void loginMissingPasswordTest() {
            Map<String, Object> credentials = new HashMap<>();
            credentials.put("username", "admin");
            given()
                    .contentType(ContentType.JSON)
                    .body(credentials)
                    .when()
                    .post("/api/user/login")
                    .then()
                    .assertThat()
                    .statusCode(400);
        }

        @Test
        void loginWrongUsernameTest() {
            Map<String, Object> credentials = new HashMap<>();
            credentials.put("username", "abc");
            credentials.put("password", "password");
            given()
                    .contentType(ContentType.JSON)
                    .body(credentials)
                    .when()
                    .post("/api/user/login")
                    .then()
                    .assertThat()
                    .statusCode(404);
        }

        @Test
        void loginWrongPasswordTest() {
            Map<String, Object> credentials = new HashMap<>();
            credentials.put("username", "admin");
            credentials.put("password", "abc");
            given()
                    .contentType(ContentType.JSON)
                    .body(credentials)
                    .when()
                    .post("/api/user/login")
                    .then()
                    .assertThat()
                    .statusCode(401);
        }

        @Test
        void loginWrongUsernameAndPasswordTest() {
            Map<String, Object> credentials = new HashMap<>();
            credentials.put("username", "abc");
            credentials.put("password", "abc");
            given()
                    .contentType(ContentType.JSON)
                    .body(credentials)
                    .when()
                    .post("/api/user/login")
                    .then()
                    .assertThat()
                    .statusCode(404);
        }

        @Test
        void loginWrongUsernameAndPasswordEmptyTest() {
            Map<String, Object> credentials = new HashMap<>();
            credentials.put("username", "");
            credentials.put("password", "");
            given()
                    .contentType(ContentType.JSON)
                    .body(credentials)
                    .when()
                    .post("/api/user/login")
                    .then()
                    .assertThat()
                    .statusCode(400);
        }

        @Test
        void loginWrongUsernameAndPasswordNullTest() {
            Map<String, Object> credentials = new HashMap<>();
            credentials.put("username", null);
            credentials.put("password", null);
            given()
                    .contentType(ContentType.JSON)
                    .body(credentials)
                    .when()
                    .post("/api/user/login")
                    .then()
                    .assertThat()
                    .statusCode(400);
        }

        @Test
        void loginWrongUsernameAndPasswordMissingTest() {
            Map<String, Object> credentials = new HashMap<>();
            given()
                    .contentType(ContentType.JSON)
                    .body(credentials)
                    .when()
                    .post("/api/user/login")
                    .then()
                    .assertThat()
                    .statusCode(400);
        }
    }

    @Nested
    class ChangePassword {
        @Test
        void changePasswordPositiveTest() {
            Map<String, Object> password = new HashMap<>();
            password.put("newPassword", "newPassword");
            given()
                    .header("Authorization", "Bearer " + retrieveToken())
                    .contentType(ContentType.JSON)
                    .body(password)
                    .when()
                    .put("/api/user/id/" + adminId + "/change_password")
                    .then()
                    .assertThat()
                    .statusCode(200);
            // Should not be able to log in using old password
            Map<String, Object> credentials = new HashMap<>();
            credentials.put("username", "admin");
            credentials.put("password", "password");
            given()
                    .contentType(ContentType.JSON)
                    .body(credentials)
                    .when()
                    .post("/api/user/login")
                    .then()
                    .assertThat()
                    .statusCode(401);

            adminPassword = "newPassword";
            password.put("newPassword", "password");
            given()
                    .header("Authorization", "Bearer " + retrieveToken())
                    .contentType(ContentType.JSON)
                    .body(password)
                    .when()
                    .put("/api/user/id/" + adminId + "/change_password")
                    .then()
                    .assertThat()
                    .statusCode(200);
        }

        @Test
        void changePasswordEmptyBody() {
            Map<String, Object> body = new HashMap<>();
            given()
                    .header("Authorization", "Bearer " + retrieveToken())
                    .contentType(ContentType.JSON)
                    .body(body)
                    .when()
                    .put("/api/user/id/" + adminId + "/change_password")
                    .then()
                    .assertThat()
                    .statusCode(400);
        }
    }
}
