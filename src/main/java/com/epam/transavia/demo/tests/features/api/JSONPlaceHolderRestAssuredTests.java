package com.epam.transavia.demo.tests.features.api;


import com.epam.transavia.demo.tests.features.api.bo.PlaceHolderUser;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.given;

public class JSONPlaceHolderRestAssuredTests {

    private static final String BASE_URI = "https://jsonplaceholder.typicode.com";
    private static final Logger apiLogger = LogManager.getLogger("ApiTests");
    private static final String CLASS_NAME = JSONPlaceHolderRestAssuredTests.class.getSimpleName();
    private Response getResponse;
    private Response postResponse;


    @BeforeTest
    public void initTests() {
        apiLogger.debug(CLASS_NAME + ": initTests started");
        RestAssured.baseURI = BASE_URI;
    }

    @Test(description = "get response")
    public void initGetResponse() {
        apiLogger.info(CLASS_NAME + ": initializing GET request...");
        getResponse = given().get("/users").andReturn();

    }

    @Test(description = "post response")
    public void initPostResponse() {
        apiLogger.info(CLASS_NAME + ": initializing POST request...");
        postResponse = given().body(PlaceHolderUser.createTestUser()).post("/users").andReturn();
    }

    @Test(dependsOnMethods = "initPostResponse")
    public void checkPostStatusCode(){
        apiLogger.info("Checking POST status code, actual is: " + postResponse.getStatusCode());
        Assert.assertEquals(201, postResponse.getStatusCode());
    }

    @Test(dependsOnMethods = "initPostResponse")
    public void checkPostBody(){
        apiLogger.info("Checking POST status body, actual is: " + postResponse.getBody().prettyPrint());
        Assert.assertEquals(11, postResponse.as(PlaceHolderUser.class).getId(), "Posted user ID must be equal to 11!");
    }


    @Test(dependsOnMethods = "initGetResponse")
    public void checkGetStatusCode() {
        apiLogger.info("Checking GET status code, actual is: " + getResponse.getStatusCode());
        Assert.assertEquals(200, getResponse.getStatusCode(), "Status code does not equal 200, returned " + getResponse.getStatusCode());
    }

    @Test(dependsOnMethods = "initGetResponse")
    public void checkGetContentTypeHeader() {
       Assert.assertEquals(getResponse.getContentType(), "application/json; charset=utf-8", "Content type does not equal application/json");
    }

    @Test(dependsOnMethods = "initGetResponse")
    public void checkCookiesNotEmpty() {
        apiLogger.info("Checked cookies are present in GET: " + getResponse.getHeader("set-cookie"));
        Assert.assertFalse(getResponse.getHeader("set-cookie").isEmpty(), "Cookies are empty.");
    }

    @Test(dependsOnMethods = "initGetResponse")
    public void checkUsersCount(){
        PlaceHolderUser[] users = getResponse.as(PlaceHolderUser[].class);
        apiLogger.info("Parsed GET users, count is " + users.length);
        Assert.assertEquals(users.length, 10, "Users count does not match to the expected.");
    }


}
