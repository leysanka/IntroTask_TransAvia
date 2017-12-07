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
    private static final String USERS_URI = "/users"; //adding to base uri
    private static final Logger apiLogger = LogManager.getLogger("ApiTests");
    private static final String CONTENT_TYPE = "application/json; charset=utf-8";
    private static final String SET_COOKIE_HEADER = "set-cookie";
    private static final String CLASS_NAME = JSONPlaceHolderRestAssuredTests.class.getSimpleName();

    private static final int POST_SUCCESS_CODE = 201;
    private static final int GET_SUCCESS_CODE = 200;
    private static final int POST_USER_ID = 11;
    private static final int USERS_COUNT = 10;

    private Response getResponse;
    private Response postResponse;


    @BeforeTest
    public void initRequests() {
        RestAssured.baseURI = BASE_URI;
        apiLogger.info(CLASS_NAME + ": initializing GET request...");

        getResponse = given().get(USERS_URI).andReturn();

        apiLogger.info(CLASS_NAME + ": initializing POST request...");

        postResponse = given().body(PlaceHolderUser.createTestUser()).post(USERS_URI).andReturn();
    }

    @Test
    public void checkPostStatusCode(){
        apiLogger.info("Checking POST status code, actual is: " + postResponse.getStatusCode());
        Assert.assertEquals(POST_SUCCESS_CODE, postResponse.getStatusCode());
    }

   @Test
    public void checkPostBody(){
        apiLogger.info("Checking POST status body, actual is: " + postResponse.getBody().prettyPrint());
        Assert.assertEquals(POST_USER_ID, postResponse.as(PlaceHolderUser.class).getId(), "Posted user ID must be equal to 11!");
    }

    @Test
    public void checkGetStatusCode() {
        apiLogger.info("Checking GET status code, actual is: " + getResponse.getStatusCode());
        Assert.assertEquals(GET_SUCCESS_CODE, getResponse.getStatusCode(), "Status code does not equal 200, returned "
                                                                           + getResponse.getStatusCode());
    }

   @Test
    public void checkGetContentTypeHeader() {
       Assert.assertEquals(getResponse.getContentType(), CONTENT_TYPE, "Content type does not equal application/json");
    }

   @Test
    public void checkCookiesNotEmpty() {
        apiLogger.info("Checked cookies are present in GET: " + getResponse.getHeader(SET_COOKIE_HEADER));
        Assert.assertFalse(getResponse.getHeader(SET_COOKIE_HEADER).isEmpty(), "Cookies are empty.");
    }

    @Test
    public void checkUsersCount(){
        PlaceHolderUser[] users = getResponse.as(PlaceHolderUser[].class);
        apiLogger.info("Parsed GET users, count is " + users.length);
        Assert.assertEquals(users.length, USERS_COUNT, "Users count does not match to the expected.");
    }
}
