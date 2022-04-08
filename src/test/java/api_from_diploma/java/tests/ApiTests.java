package api_from_diploma.java.tests;

import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static data.TestData.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static specs.Specs.*;

public class ApiTests extends TestBase {

    @Test
    @Tag("api")
    @DisplayName("User list test")
    @Owner("berezkindv")
    void userListTest() {
        given()
                .spec(requestSpec)
                .when()
                .get("/users?page=2")
                .then()
                .spec(responseSpec200)
                .body("total", is(12));
    }

    @Test
    @Tag("api")
    @DisplayName("Single user test")
    @Owner("berezkindv")
    void singleUserTest() {
        given()
                .spec(requestSpec)
                .when()
                .get("/users/2")
                .then()
                .spec(responseSpec200)
                .body("data.id", is(2))
                .body("data.email", is("janet.weaver@reqres.in"))
                .body("data.first_name", is("Janet"))
                .body("data.last_name", is("Weaver"));
    }

    @Test
    @Tag("api")
    @DisplayName("Single user not found test")
    @Owner("berezkindv")
    void singleUserNotFoundTest() {
        given()
                .spec(requestSpec)
                .when()
                .get("/users/23")
                .then()
                .spec(responseSpec404);
    }

    @Test
    @Tag("api")
    @DisplayName("Resources list test")
    @Owner("berezkindv")
    void resourceListTest() {
        given()
                .spec(requestSpec)
                .when()
                .get("/unknown")
                .then()
                .spec(responseSpec200)
                .body("total", is(12));
    }

    @Test
    @Tag("api")
    @DisplayName("Single resources list test")
    @Owner("berezkindv")
    void singleResourceTest() {
        given()
                .spec(requestSpec)
                .when()
                .get("/unknown/2")
                .then()
                .spec(responseSpec200)
                .body("data.name", is("fuchsia rose"))
                .body("data.color", is("#C74375"));
    }

    @Test
    @Tag("api")
    @DisplayName("Single resources not found test")
    @Owner("berezkindv")
    void singleResourceNotFoundTest() {
        given()
                .spec(requestSpec)
                .when()
                .get("/unknown/23")
                .then()
                .spec(responseSpec404);
    }

    @Test
    @Tag("api")
    @DisplayName("Create user test")
    @Owner("berezkindv")
    void createUserTest() {
        given()
                .spec(requestSpec)
                .body(createUserData)
                .when()
                .post("/user")
                .then()
                .spec(responseSpec201)
                .body("name", is("morpheus"))
                .body("job", is("leader"))
                .body("id", notNullValue());
    }

    @Test
    @Tag("api")
    @DisplayName("Update user test with PUT request")
    @Owner("berezkindv")
    void updateUserPutTest() {
        given()
                .spec(requestSpec)
                .body(updateUserData)
                .when()
                .put("/user/2")
                .then()
                .spec(responseSpec200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
    }

    @Test
    @Tag("api")
    @DisplayName("Update user test with PATCH request")
    @Owner("berezkindv")
    void updateUserPatchTest() {
        given()
                .spec(requestSpec)
                .body(updateUserData)
                .when()
                .patch("/user/2")
                .then()
                .spec(responseSpec200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
    }

    @Test
    @Tag("api")
    @DisplayName("Delete user test")
    @Owner("berezkindv")
    void deleteUserTest() {
        given()
                .spec(requestSpec)
                .when()
                .delete("/user/2")
                .then()
                .spec(responseSpec204);
    }

    @Test
    @Tag("api")
    @DisplayName("Successful register user test")
    @Owner("berezkindv")
    void registerSuccessfulTest() {
        given()
                .spec(requestSpec)
                .body(successfulRegisterUser)
                .when()
                .post("/register")
                .then()
                .spec(responseSpec200)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    @Tag("api")
    @DisplayName("Unsuccessful register user test")
    @Owner("berezkindv")
    void registerUnsuccessfulTest() {
        given()
                .spec(requestSpec)
                .body(unsuccessfulRegisterUser)
                .when()
                .post("/register")
                .then()
                .spec(responseSpec400)
                .body("error", is("Missing password"));
    }
}
