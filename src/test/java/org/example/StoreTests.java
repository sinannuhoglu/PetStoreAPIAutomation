package org.example;

import io.restassured.response.Response;
import org.example.base.BaseTest;
import org.testng.annotations.Test;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class StoreTests extends BaseTest {

    @Test(priority = 1, description = "GET - /store/inventory - Retrieve inventory status")
    public void getInventoryStatus() {
        Response response = Get("/v2/store/inventory");
        assertEquals(response.getStatusCode(), 200, "Failed to fetch inventory.");
    }

    @Test(priority = 2, description = "POST - /store/order - Place a new order")
    public void placeOrder() {
        Map<String, Object> orderPayload = createOrderPayload();
        Response response = Post("/v2/store/order", orderPayload, getJsonHeaders());

        assertEquals(response.getStatusCode(), 200, "Order placement failed.");
        assertEquals(response.jsonPath().getInt("id"), ORDER_ID, "Order ID mismatch.");
    }

    @Test(priority = 3, dependsOnMethods = "placeOrder", description = "GET - /store/order/{orderId} - Retrieve order details")
    public void getOrderById() {
        Response response = Get("/v2/store/order/" + ORDER_ID);

        assertEquals(response.getStatusCode(), 200, "Failed to retrieve order.");
        assertEquals(response.jsonPath().getInt("petId"), PET_ID, "Pet ID in order does not match.");
    }

    @Test(priority = 4, description = "DELETE - /store/order/{orderId} - Delete the order")
    public void deleteOrder() {
        Response response = Delete("/v2/store/order/" + ORDER_ID);
        assertEquals(response.getStatusCode(), 200, "Order deletion failed.");
    }

    @Test(priority = 5, description = "GET - /store/order/{orderId} - Verify order deletion")
    public void verifyDeletedOrder() {
        Response response = Get("/v2/store/order/" + ORDER_ID);
        int statusCode = response.getStatusCode();

        assertEquals(statusCode, 404, "The order should have been deleted.");
        System.out.println("Order deletion confirmed. Status code: " + statusCode);
    }

    // ----------------- Helper Methods -----------------

    private Map<String, Object> createOrderPayload() {
        Map<String, Object> order = new HashMap<>();
        order.put("id", ORDER_ID);
        order.put("petId", PET_ID);
        order.put("quantity", 2);
        order.put("shipDate", Instant.now().toString());
        order.put("status", "placed");
        order.put("complete", true);
        return order;
    }

    private Map<String, Object> getJsonHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("accept", "application/json");
        headers.put("Content-Type", "application/json");
        return headers;
    }
}
