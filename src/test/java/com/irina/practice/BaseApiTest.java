package com.irina.practice;

import com.irina.practice.config.Config;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

public class BaseApiTest {

    protected static RequestSpecification requestSpec;

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = Config.apiBaseUrl();

        requestSpec = new RequestSpecBuilder()
                .addHeader("Accept", Config.accept())
                .addHeader("Content-Type", Config.contentType())
                .build();
    }
}
