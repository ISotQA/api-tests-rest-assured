package com.irina.practice;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UsersApiTest extends BaseApiTest {

    @Test
    void getUserById_returnsCorrectUser() {
        given()
                .spec(requestSpec)
                .pathParam("id", 1)
                .when()
                .get("/users/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("username", notNullValue())
                .body("email", notNullValue())
                .body("address.city", notNullValue());
    }

    @Test
    void getAllUsers_returnsNonEmptyList() {
        given()
                .spec(requestSpec)
                .when()
                .get("/users/")
                .then()
                .statusCode(200)
                .body("size", greaterThan(0))
                .body("id", hasItem(1));
    }

    @Test
    void getUser_whenIdDoesNotExist_returns404() {
        given()
                .spec(requestSpec)
                .pathParam("id", 999999)
                .when()
                .get("/users/{id}")
                .then()
                .statusCode(404)
                .body("$", anEmptyMap());
    }

    @Test
    void getUserById_responseContentTypeIsJson() {
        given()
                .spec(requestSpec)
                .pathParam("id", 1)
                .when()
                .get("/users/{id}")
                .then()
                .statusCode(200)
                .header("Content-Type", containsString("application/json"));
    }

}
