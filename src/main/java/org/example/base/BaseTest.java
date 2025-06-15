package org.example.base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;

public class BaseTest extends BaseLibrary {

    @BeforeMethod
    public void beforeTest() {
        RestAssured.baseURI = Data.BASE_URL;
    }
}
