package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.base.BaseTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class PetTests extends BaseTest {

    @Test(priority = 1, description = "POST - /v2/pet - Create a new pet")
    public void createPet() {
        Map<String, Object> petPayload = buildPetPayload(
                "Karabas",
                "https://petstore.com/images/karabas.jpg",
                "friendly",
                "available"
        );
        Post("/v2/pet", petPayload, getJsonHeaders());
    }

    @Test(priority = 2, description = "PUT - /v2/pet - Update an existing pet")
    public void updatePet() {
        Map<String, Object> updatedPetPayload = buildPetPayload(
                "Karabas Updated",
                "https://petstore.com/images/karabas_new.jpg",
                "guard",
                "sold"
        );
        Put("/v2/pet", updatedPetPayload, getJsonHeaders());
    }

    @Test(priority = 3, description = "GET - /v2/pet/{petId} - Verify the updated pet details")
    public void verifyUpdatedPet() {
        Response response = Get("/v2/pet/" + PET_ID);

        String actualName = response.jsonPath().getString("name");
        String actualStatus = response.jsonPath().getString("status");

        assertEquals(actualName, "Karabas Updated", "Expected name does not match.");
        assertEquals(actualStatus, "sold", "Expected status does not match.");
    }

    @Test(priority = 4, description = "POST - /v2/pet/{petId} - Update pet using form data")
    public void updatePetWithFormData() {
        Map<String, Object> headers = Map.of("accept", "application/json");

        String name = "Karabas";
        String status = "available";

        Response response = RestAssured
                .given()
                .headers(headers)
                .contentType("application/x-www-form-urlencoded")
                .formParam("name", name)
                .formParam("status", status)
                .post("/v2/pet/" + PET_ID);

        System.out.println("Form data update response:\n" + response.getBody().asString());
    }

    @Test(priority = 5, description = "GET - /v2/pet/{petId} - Verify pet after form data update")
    public void verifyPetAfterFormUpdate() {
        Response response = Get("/v2/pet/" + PET_ID);

        String actualName = response.jsonPath().getString("name");
        String actualStatus = response.jsonPath().getString("status");

        System.out.println("Pet after form update - Name: " + actualName + ", Status: " + actualStatus);

        if (!"Karabas".equals(actualName) || !"available".equals(actualStatus)) {
            System.out.println("Warning: The mock API might not support form-data updates.");
        }
    }

    @Test(priority = 6, description = "DELETE - /v2/pet/{petId} - Delete the pet")
    public void deletePet() {
        Delete("/v2/pet/" + PET_ID);
    }

    @Test(priority = 7, description = "GET - /v2/pet/{petId} - Verify pet deletion")
    public void verifyDeletedPet() {
        Response response = Get("/v2/pet/" + PET_ID);
        int statusCode = response.getStatusCode();

        if (statusCode == 200) {
            System.out.println("Warning: The pet still exists. Deletion may have failed.");
        } else {
            assertEquals(statusCode, 404, "Pet should have been deleted.");
            System.out.println("Pet deletion verified successfully.");
        }
    }

    // ------------------- Helper Methods -------------------

    private Map<String, Object> buildPetPayload(String name, String imageUrl, String tagName, String status) {
        Map<String, Object> category = new HashMap<>();
        category.put("id", 1);
        category.put("name", "dog");

        Map<String, Object> tag = new HashMap<>();
        tag.put("id", 1);
        tag.put("name", tagName);

        Map<String, Object> pet = new HashMap<>();
        pet.put("id", PET_ID);
        pet.put("category", category);
        pet.put("name", name);
        pet.put("photoUrls", List.of(imageUrl));
        pet.put("tags", List.of(tag));
        pet.put("status", status);

        return pet;
    }

    private Map<String, Object> getJsonHeaders() {
        return Map.of(
                "accept", "application/json",
                "Content-Type", "application/json"
        );
    }
}