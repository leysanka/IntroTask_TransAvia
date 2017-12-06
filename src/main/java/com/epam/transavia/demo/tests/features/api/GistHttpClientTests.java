package com.epam.transavia.demo.tests.features.api;

import com.epam.transavia.demo.tests.features.api.bo.Gist;
import com.epam.transavia.demo.tests.features.api.bo.HttpRequestType;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

public class GistHttpClientTests {

    private static final String BASE_URI = "https://api.github.com/gists";
    private static final Logger apiLogger = LogManager.getLogger("ApiTests");
    private static CloseableHttpClient httpClient;

    @BeforeTest
    public void initHttpClient() {
        httpClient = HttpClients.createDefault();
    }

    @Test
    public void setUpAuthorizedConnectionWithGet() {

        HttpGet httpGet = (HttpGet) GitService.getHttpTokenAuthorization(BASE_URI, HttpRequestType.GET);
        CloseableHttpResponse response = GitService.executeResponse(httpClient, httpGet);
        List<Gist> gists = GitService.convertArrayGistsFromJsonResponseToPOJOList(response);
        GitService.closeResponse(response);

        apiLogger.info("Response status line: " + response.getStatusLine());
        apiLogger.info("Count of gists is: " + gists.size());

        Assert.assertEquals(200, response.getStatusLine().getStatusCode(), "Status code of response in not 200.");
    }

    @Test(priority = 1)
    public void postNewGist() {
        HttpPost httpPost;
        Gist postingGist = GitService.createNewGist();

        httpPost = (HttpPost) GitService.getHttpTokenAuthorization(BASE_URI, HttpRequestType.POST);
        httpPost.setEntity(GitService.createJSONEntityFromObject(postingGist));
        CloseableHttpResponse postResponse = GitService.executeResponse(httpClient, httpPost);

        Gist resultGist = GitService.convertGistFromJsonResponseToPOJO(postResponse);
        GitService.saveGistIdToFile(resultGist);
        GitService.closeResponse(postResponse);

        apiLogger.debug("Posted gist id = " + resultGist.getId());
        apiLogger.info("Response status line: " + postResponse.getStatusLine());

        Assert.assertEquals(201, postResponse.getStatusLine().getStatusCode(), "Status code does not equal 201 after new gist POST.");
        Assert.assertEquals(postingGist.getDescription(), resultGist.getDescription(), "Description of new posted gist does not equal to the expected.");
    }

    @Test(priority = 2)
    public void updateGistWithPatch() {
        String updateGistUri = BASE_URI.concat("/").concat(GitService.getCreatedGistIdFromFile());
        HttpPatch httpPatch;

        httpPatch = (HttpPatch)GitService.getHttpTokenAuthorization(updateGistUri,HttpRequestType.PATCH);
        httpPatch.setEntity(GitService.createJSONEntityFromObject(GitService.createGistToUpdateFileContentAndNewFile()));
        CloseableHttpResponse patchResponse = GitService.executeResponse(httpClient, httpPatch);
        Gist patchedGist = GitService.convertGistFromJsonResponseToPOJO(patchResponse);
        GitService.closeResponse(patchResponse);

        apiLogger.info("Response status is: " + patchResponse.getStatusLine());
        apiLogger.debug("Patched gist id: " + patchedGist.getId());
        apiLogger.debug("Patched gist files: " + patchedGist.getFiles().toString());
        for (int i = 0; i < patchedGist.getHistory().length; i++) {
            apiLogger.debug("Patched gist files history: " + patchedGist.getHistory()[i].toString());
        }

        Assert.assertEquals(patchedGist.getId(), GitService.getCreatedGistIdFromFile(), "Unexpected gist updated.");
        Assert.assertEquals(200, patchResponse.getStatusLine().getStatusCode(), "Status code does not equal 200");
        Assert.assertEquals(2, patchedGist.getFiles().keySet().size(), "Files count must be 2 after update.");
    }


    @Test(priority = 3)
    public void starGistWithPut() {
        String starForGistUri = BASE_URI.concat("/").concat(GitService.getCreatedGistIdFromFile()).concat("/star");
        HttpPut httpPut;
        CloseableHttpResponse putResponse;

        httpPut = (HttpPut)GitService.getHttpTokenAuthorization(starForGistUri, HttpRequestType.PUT);
        putResponse = GitService.executeResponse(httpClient, httpPut);
        GitService.closeResponse(putResponse);

        Assert.assertEquals(204, putResponse.getStatusLine().getStatusCode(), "Star gist operation status code does not equal"
                                                                              + " to the expected 204.");
    }

    @Test(priority = 4, dependsOnMethods = "starGistWithPut")
    public void verifyGistIsStarred() {

        String starForGistUri = BASE_URI.concat("/").concat(GitService.getCreatedGistIdFromFile()).concat("/star");
        HttpGet httpGet;
        CloseableHttpResponse getResponse;

        httpGet = (HttpGet)GitService.getHttpTokenAuthorization(starForGistUri, HttpRequestType.GET);
        getResponse = GitService.executeResponse(httpClient, httpGet);
        GitService.closeResponse(getResponse);

        Assert.assertEquals(204, getResponse.getStatusLine().getStatusCode(), "Get Starred gist status code does not equal to the expected 204.");
    }

    @Test(priority = 5, dependsOnMethods = "postNewGist")
    public void deleteGist() {
        String gistIdToDeleteUri = BASE_URI.concat("/").concat(GitService.getCreatedGistIdFromFile());
        HttpDelete httpDelete;
        CloseableHttpResponse deleteResponse;

        httpDelete = (HttpDelete)GitService.getHttpTokenAuthorization(gistIdToDeleteUri, HttpRequestType.DELETE);
        deleteResponse = GitService.executeResponse(httpClient, httpDelete);
        GitService.closeResponse(deleteResponse);

        FileUtils.deleteQuietly(new File(GitService.getGistIdFilePath()));

        Assert.assertEquals(204, deleteResponse.getStatusLine().getStatusCode(), "Status code of Delete does not equal to the expected 204.");

    }

}

