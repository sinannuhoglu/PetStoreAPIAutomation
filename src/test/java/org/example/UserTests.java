package org.example;

import io.restassured.response.Response;
import org.example.base.BaseTest;
import org.example.base.Data;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class UserTests extends BaseTest {

    @Test(priority = 1, description = "POST - /user - Create a new user")
    public void createUser() {
        Map<String, Object> user = new HashMap<>();
        user.put("id", Data.USER_ID);
        user.put("username", Data.USERNAME);
        user.put("firstName", Data.FIRST_NAME);
        user.put("lastName", Data.LAST_NAME);
        user.put("email", Data.EMAIL);
        user.put("password", Data.PASSWORD);
        user.put("phone", Data.PHONE);
        user.put("userStatus", 1);

        Response response = Post("/v2/user", user, getJsonHeader());

        assertEquals(response.getStatusCode(), 200);
        assertEquals(response.jsonPath().getString("message"), String.valueOf(Data.USER_ID));
    }

    @Test(priority = 2, description = "GET - /user/{username} - Retrieve created user")
    public void getUserByUsername() {
        Response response = Get("/v2/user/" + Data.USERNAME);

        assertEquals(response.getStatusCode(), 200);
        assertEquals(response.jsonPath().getString("username"), Data.USERNAME);
    }

    @Test(priority = 3, description = "PUT - /user/{username} - Update user info")
    public void updateUser() {
        Map<String, Object> updatedUser = new HashMap<>();
        updatedUser.put("id", Data.USER_ID);
        updatedUser.put("username", Data.USERNAME);
        updatedUser.put("firstName", Data.UPDATED_FIRST_NAME);
        updatedUser.put("lastName", Data.LAST_NAME);
        updatedUser.put("email", Data.UPDATED_EMAIL);
        updatedUser.put("password", Data.UPDATED_PASSWORD);
        updatedUser.put("phone", Data.UPDATED_PHONE);
        updatedUser.put("userStatus", 1);

        Response response = Put("/v2/user/" + Data.USERNAME, updatedUser, getJsonHeader());

        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 4, description = "GET - /user/{username} - Verify updated user info")
    public void verifyUpdatedUser() {
        Response response = Get("/v2/user/" + Data.USERNAME);

        assertEquals(response.getStatusCode(), 200);
        assertEquals(response.jsonPath().getString("firstName"), Data.UPDATED_FIRST_NAME);
        assertEquals(response.jsonPath().getString("email"), Data.UPDATED_EMAIL);
    }

    @Test(priority = 5, description = "DELETE - /user/{username} - Delete the user")
    public void deleteUser() {
        Response response = Delete("/v2/user/" + Data.USERNAME);
        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 6, description = "GET - /user/{username} - Verify user is deleted")
    public void verifyDeletedUser() {
        Response response = Get("/v2/user/" + Data.USERNAME);
        assertEquals(response.getStatusCode(), 404);
    }

    private Map<String, Object> getJsonHeader() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("accept", "application/json");
        headers.put("Content-Type", "application/json");
        return headers;
    }
}
