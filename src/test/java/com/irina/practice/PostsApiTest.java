package com.irina.practice;

import com.irina.practice.config.Config;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class PostsApiTest extends BaseApiTest{

    @Nested
    class PositiveTests {

        @Test
        void createPost_return201AndCorrectBody() {
            // тело запроса как Map → REST Assured сам превратит в JSON
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("title", "Irina test post");
            requestBody.put("body", "Some test content");
            requestBody.put("userId", 1);

            given()
                    .log().all()
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .body(requestBody)
                    .when()
                    .post("/posts")
                    .then()
                    .log().all()
                    .statusCode(201)
                    .body("title", equalTo("Irina test post"))
                    .body("body", equalTo("Some test content"))
                    .body("userId", equalTo(1))
                    .body("id", notNullValue());

        }

        @Test
        void getPostById_returnsCorrectPost() {
            given()
                    .log().all()
                    .when()
                    .get("/posts/1")
                    .then()
                    .log().all()
                    .statusCode(200)
                    .body("id", equalTo(1))
                    .body("title", notNullValue())
                    .body("body", notNullValue());
        }

        @Test
        void getAllPosts_returnsNonEmptyList() {
            given()
                    .log().all()
                    .when()
                    .get("/posts")
                    .then()
                    .log().all()
                    .statusCode(200)
                    .body("size()", greaterThan(0))
                    .body("id", hasItem(1));
        }

        @Test
        void updatePost_returnsUpdatedFields() {
            Map<String, Object> body = new HashMap<>();
            body.put("id", 1);
            body.put("title", "Updated title");
            body.put("body", "Updated body");
            body.put("userId", 1);

            given()
                    .log().all()
                    .header("Content-Type", "application/json")
                    .body(body)
                    .when()
                    .put("/posts/1")
                    .then()
                    .log().all()
                    .statusCode(200)
                    .body("title", equalTo("Updated title"))
                    .body("body", equalTo("Updated body"));
        }

        @Test
        void deletePost_returns200() {
            given()
                    .log().all()
                    .when()
                    .delete("/posts/1")
                    .then()
                    .log().all()
                    .statusCode(200);
        }

        @Test
        void getPostById_matchesSchema() {
            given()
                    .spec(requestSpec)
                    .pathParam("id", 1)
                    .when()
                    .get("/posts/{id}")
                    .then()
                    .statusCode(200)
                    .body(matchesJsonSchemaInClasspath("schemas/post.json"));
        }
    }

    @Nested
    class NegativeTests {

        @Test
        void createPost_onWrongEndpoint_returns404() {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("title", "Irina test post");
            requestBody.put("body", "Some test content");
            requestBody.put("userId", 1);

            given()
                    .log().all()
                    .header("Content-Type", "application/json")
                    .header("body", "application/json")
                    .body(requestBody)
                    .when()
                    .post("/postssss")
                    .then()
                    .log().all()
                    .statusCode(404);
        }

        @Test
        void getPost_whenIdDoesNotExist_returns404() {
            given()
                    .spec(requestSpec)
                    .pathParam("id", 999999)
            .when()
                    .get("/posts/{id}")
            .then()
                    .statusCode(404)
                    .body("$", anEmptyMap());
        }

        @Test
        void getPost_withNonNumericId_returns404() {
            given()
                    .spec(requestSpec)
                    .pathParam("id", "abc")
            .when()
                    .get("/posts/{id}")
            .then()
                    .statusCode(404);
        }

        @Test
        void getPosts_withImpossibleUserId_returnsEmptyList() {
            given()
                    .spec(requestSpec)
                    .queryParam("userId", 99999)
            .when()
                    .get("/posts")
            .then()
                    .statusCode(200)
                    .body("$", empty());
        }

        @Test
        void createPost_withEmptyJson_returns201AndHasId() {
            given()
                    .spec(requestSpec)
                    .body("{}")
            .when()
                    .post("/posts")
            .then()
                    .statusCode(201)
                    .body("id", notNullValue());
        }

        @Test
        void createPost_withWrongContentType_returnsExpectedStatus() {
            given()
                    .spec(requestSpec)
                    .header("Content-Type", "text/plain")
                    .body("hello")
            .when()
                    .post("/posts")
            .then()
                    .statusCode(anyOf(is(201), is(400), is(415)));
        }

        @Test
        void getNonExistingComment_returns404() {
            given()
                    .spec(requestSpec)
            .when()
                    .post("/comments/999999")
            .then()
                    .statusCode(404);
        }

    }
}
