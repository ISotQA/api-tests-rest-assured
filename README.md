# API Tests with REST Assured

Pet project for practicing API automation testing in Java using REST Assured. The project is created for learning purposes and demonstrates basic approaches to testing REST APIs.

As a test API, a public mock service is used: https://jsonplaceholder.typicode.com

---

## Technologies

- Java
- Maven
- JUnit 5
- REST Assured
- Hamcrest
- Jackson (JSON serialization)

---

## What is covered

The project contains API tests for the following resources.

### Covered test scenarios

**/posts**
- Positive scenarios:
    - Get post by id
    - Get list of posts
    - Create, update and delete post
- Negative scenarios:
    - Request to invalid endpoint (404)
    - Request with non-existing or invalid id (404)
    - Request with incorrect content type
- Contract testing:
    - JSON schema validation for `GET /posts/{id}`

**/comments**
- Filtering using query parameters
- Validation that all returned items match the filter condition

Both positive and negative scenarios are covered.

---

## Project structure

src/test/java
- com.irina.practice
    - BaseApiTest.java
    - PostsApiTest.java

src/test/resources
- config.properties
- schemas
    - post.json
    
---

## How to run tests

Run all tests using Maven:


Or run individual tests directly from IntelliJ IDEA.

---

## Notes

JSONPlaceholder is a mock API.  
It does not persist data, so POST, PUT and DELETE requests return simulated responses.  
The project is intended for practicing API testing techniques, request/response validation and test structure.

---

## Purpose

The main goal of this project is to practice API automation testing, understand REST principles and prepare for technical interviews.