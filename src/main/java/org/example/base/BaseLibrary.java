package org.example.base;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.Map;

public class BaseLibrary extends Data {

    // Common GET request without params
    @Step("GET request to: {urlPath}")
    public Response Get(String urlPath) {
        Response response = RestAssured
                .given()
                .relaxedHTTPSValidation()
                .when()
                .get(urlPath);

        logResponse(response);
        return response;
    }

    // GET request with query parameters
    @Step("GET request to: {urlPath} with query params")
    public Response Get(String urlPath, Map<String, Object> queryParams) {
        Response response = RestAssured
                .given()
                .relaxedHTTPSValidation()
                .queryParams(queryParams)
                .when()
                .get(urlPath);

        logResponse(response);
        return response;
    }

    @Step("POST request to: {urlPath}")
    public Response Post(String urlPath, Map<String, Object> body, Map<String, Object> headers) {
        Response response = RestAssured
                .given()
                .relaxedHTTPSValidation()
                .headers(headers)
                .body(body)
                .when()
                .post(urlPath);

        logResponse(response);
        return response;
    }

    @Step("PUT request to: {urlPath}")
    public Response Put(String urlPath, Map<String, Object> body, Map<String, Object> headers) {
        Response response = RestAssured
                .given()
                .relaxedHTTPSValidation()
                .headers(headers)
                .body(body)
                .when()
                .put(urlPath);

        logResponse(response);
        return response;
    }

    @Step("DELETE request to: {urlPath}")
    public Response Delete(String urlPath) {
        Response response = RestAssured
                .given()
                .relaxedHTTPSValidation()
                .when()
                .delete(urlPath);

        logResponse(response);
        return response;
    }

    @Step("POST Form request to: {urlPath}")
    public Response PostForm(String urlPath, Map<String, Object> formData, Map<String, Object> headers) {
        Response response = RestAssured
                .given()
                .relaxedHTTPSValidation()
                .headers(headers)
                .formParams(formData)
                .when()
                .post(urlPath);

        logResponse(response);
        return response;
    }

    // Assertion helpers
    @Step("Assert equals (String): expected = {expected}, actual = {actual}")
    public void AssertEquals(String actual, String expected) {
        Assert.assertEquals(actual, expected);
    }

    @Step("Assert equals (int): expected = {expected}, actual = {actual}")
    public void AssertEquals(int actual, int expected) {
        Assert.assertEquals(actual, expected);
    }

    // Private logger method
    private void logResponse(Response response) {
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
    }
}
