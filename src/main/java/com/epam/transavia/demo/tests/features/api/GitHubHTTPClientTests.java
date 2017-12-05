package com.epam.transavia.demo.tests.features.api;

import com.epam.transavia.demo.core.exceptions.HttpClientException;
import com.epam.transavia.demo.tests.features.api.bo.Gist;
import org.apache.commons.io.FileUtils;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class GitHubHTTPClientTests {

    private static final String BASE_URI = "https://api.github.com/gists";
    private static final Logger apiLogger = LogManager.getLogger("ApiTests");

    @Test
    public void setUpAuthorizedConnection() throws IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(BASE_URI);
        httpGet.setHeader("Authorization", System.getenv("OAUTH_TOKEN"));

        CloseableHttpResponse response = httpclient.execute(httpGet);
        apiLogger.info("Response status line: " + response.getStatusLine());
        List<Gist> gists = GitHubService.convertArrayGistsFromJsonResponseToPOJOList(response);
        apiLogger.info("Count of gists is: " + gists.size());
        Assert.assertEquals(200, response.getStatusLine().getStatusCode(), "Status code of response in not 200.");
        response.close();
    }

    @Test(priority = 1)
    public void postNewGist() {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(BASE_URI);
        httpPost.setHeader("Authorization", System.getenv("OAUTH_TOKEN"));
        httpPost.setEntity(GitHubService.createJSONEntityFromObject(GitHubService.createNewGist()));
        try {
            CloseableHttpResponse postResponse = httpclient.execute(httpPost);
            Gist resultGist = GitHubService.convertGistFromJsonResponseToPOJO(postResponse);
            apiLogger.info("Response status line: " + postResponse.getStatusLine());
            System.out.println(resultGist.getId());
            GitHubService.saveGistIdToFile(resultGist);
            postResponse.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Test(priority = 2)
    public void updateGistWithPatch() {
        String gistIdToUpdate = GitHubService.getCreatedGistIdFromFile();
        Gist patchedGist = null;
        StatusLine responseStatus = null;

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPatch httpPatch = new HttpPatch(BASE_URI.concat("/").concat(gistIdToUpdate));
        httpPatch.setHeader("Authorization", System.getenv("OAUTH_TOKEN"));
        httpPatch.setEntity(GitHubService.createJSONEntityFromObject(GitHubService.createGistToUpdateFileContentAndNewFile()));
        try {
            CloseableHttpResponse patchResponse = httpclient.execute(httpPatch);
            responseStatus = patchResponse.getStatusLine();
            patchedGist = GitHubService.convertGistFromJsonResponseToPOJO(patchResponse);
            patchResponse.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        apiLogger.info("Response status is: " + responseStatus);
        apiLogger.info("Patched gist id: " + patchedGist.getId());
        apiLogger.info("Patched gist files: " + patchedGist.getFiles().toString());
        for (int i = 0; i < patchedGist.getHistory().length; i++) {
            apiLogger.info("Patched gist files history: " + patchedGist.getHistory()[i].toString());
        }

        Assert.assertEquals(patchedGist.getId(), gistIdToUpdate, "Unexpected gist updated.");
        Assert.assertEquals(200, responseStatus.getStatusCode(), "Status code does not equal 200");
        Assert.assertEquals(2, patchedGist.getFiles().keySet().size(), "Files count must be 2 after update.");
    }


    @Test(priority = 3)
    public void starGistWithPut() {
        String gistIdToUpdate = GitHubService.getCreatedGistIdFromFile();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(BASE_URI.concat("/").concat(gistIdToUpdate).concat("/star"));
        httpPut.setHeader("Authorization", System.getenv("OAUTH_TOKEN"));
        CloseableHttpResponse putResponse = null;
        try {
            putResponse = httpclient.execute(httpPut);
        } catch (IOException e) {
            apiLogger.error("HttpClient failed to execute Put: " + e.getMessage());
            throw new HttpClientException("HttpClient failed to execute Put: " + e.getMessage());
        }

        Assert.assertEquals(204, putResponse.getStatusLine().getStatusCode(), "Star gist operation status code does not equal"
                                                                              + " to the expected 204.");
    }

    //@Test(dependsOnMethods = "starGistWithPut")
    @Test(priority = 4)
    public void verifyGistIsStarred() {
        String gistIdToUpdate = GitHubService.getCreatedGistIdFromFile();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(BASE_URI.concat("/").concat(gistIdToUpdate).concat("/star"));
        httpGet.setHeader("Authorization", System.getenv("OAUTH_TOKEN"));

        CloseableHttpResponse getResponse = null;
        try {
            getResponse = httpclient.execute(httpGet);
        } catch (IOException e) {
            apiLogger.error("HttpClient failed to execute Get: " + e.getMessage());
            throw new HttpClientException("HttpClient failed to execute Get: " + e.getMessage());
        }

        Assert.assertEquals(204, getResponse.getStatusLine().getStatusCode(), "Get Starred gist status code does not equal" + " to the expected 204.");

    }


    @Test(priority = 5)
    public void deleteGist() {
        String gistIdToUpdate = GitHubService.getCreatedGistIdFromFile();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(BASE_URI.concat("/").concat(gistIdToUpdate));
        httpDelete.setHeader("Authorization", System.getenv("OAUTH_TOKEN"));
        CloseableHttpResponse deleteResponse = null;
        try {
            deleteResponse = httpclient.execute(httpDelete);

        } catch (IOException e) {
            apiLogger.error("HttpClient failed to execute Delete: " + e.getMessage());
            throw new HttpClientException("HttpClient failed to execute Delete: " + e.getMessage());
        }
        FileUtils.deleteQuietly(new File(GitHubService.getGistIdFile()));

        Assert.assertEquals(204, deleteResponse.getStatusLine().getStatusCode(), "Status code of Delete does not equal to the expected 204.");

    }

}

