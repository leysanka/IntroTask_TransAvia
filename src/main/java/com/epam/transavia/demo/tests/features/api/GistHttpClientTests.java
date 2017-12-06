package com.epam.transavia.demo.tests.features.api;

import com.epam.transavia.demo.tests.features.api.bo.Gist;
import com.epam.transavia.demo.tests.features.api.services.GistService;
import com.epam.transavia.demo.util.GistUtils;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

import java.util.List;

public class GistHttpClientTests {

    private static final String BASE_URI = "https://api.github.com/gists";
    private static final Logger apiLogger = LogManager.getLogger("ApiTests");
    private static final String USER_LOGIN = "leysanka";
    private static final int POST_SUCCESS_CODE = 201;
    private static final int GET_SUCCESS_CODE = 200;
    private static final int PATCH_SUCCESS_CODE = 200;
    CloseableHttpResponse getGistsResponse, getStarResponse, postResponse, putStarResponse, patchResponse, deleteResponse;
    List<Gist> baseGists;
    Gist postingGist, postedGist, updateGist, updatedGist;

    private GistService service = new GistService();

    @BeforeGroups(groups = "getBaseUri", description = "Execute authorized GET to BASE_URI, fetch response and list of all gists of the user.")
    public void getBaseUriResponseAndGists() {
        getGistsResponse = service.getAuthorizedGetResponse(BASE_URI);
        baseGists = GistUtils.convertArrayGistsFromJsonResponseToPOJOList(getGistsResponse);
        service.closeResponse(getGistsResponse);
        apiLogger.debug("Before method called.");
    }
    //@BeforeGroups(groups = {"postNewGist", "patchGist"})
    @BeforeGroups(groups = "postNewGist")
    public void postNewGist() {
        postingGist = GistService.createNewGist();
        postResponse = service.getAuthorizedPostResponse(BASE_URI, GistUtils.convertObjToJsonStringEntity(postingGist));
        postedGist = GistUtils.convertGistFromJsonResponseToPOJO(postResponse);
        GistUtils.saveGistIdToFile(postedGist);
        service.closeResponse(postResponse);
        apiLogger.info("Before postGist method called.");
    }

    @BeforeGroups(groups = "patchGist")
    public void updateGistViaPatch() {
        if(! FileUtils.getFile(GistUtils.getGistIdFilePath()).exists() ) {
            postNewGist();
        }
        String updateGistUri = BASE_URI.concat("/").concat(GistUtils.getCreatedGistIdFromFile());
        updateGist = GistService.createGistUpdateFileContentAndNewFile();
        patchResponse = service.getAuthorizedPatchResponse(updateGistUri, GistUtils.convertObjToJsonStringEntity(updateGist));
        updatedGist = GistUtils.convertGistFromJsonResponseToPOJO(patchResponse);
        service.closeResponse(patchResponse);
    }

    @Test(groups = "getBaseUri", description = "Check Status Code is valid.")
    public void checkGetBaseUriStatusCode() {
        Assert.assertEquals(GET_SUCCESS_CODE, getGistsResponse.getStatusLine().getStatusCode(), "Status code of response in not 200.");
        apiLogger.info("checkGetBaseUriStatusCode");
    }

    @Test(groups = "getBaseUri", description = "Check whether the expected user is authorized.")
    public void checkGetBaseUriAuthorizedUser() {

        Assert.assertEquals(baseGists.get(0).getOwner().getLogin(), USER_LOGIN, "Authorized Owner login does not equal to the expected " + USER_LOGIN);
        apiLogger.info("checkGetBaseUriAuthorizedUser");
    }

    @Test(groups = "postNewGist", description = "Check status code is valid after POST execution")
    public void checkPostStatusCode() {
        apiLogger.info("Response status line: " + postResponse.getStatusLine());
        Assert.assertEquals(POST_SUCCESS_CODE, postResponse.getStatusLine().getStatusCode(), "Status code after new gist POST does not equal " + POST_SUCCESS_CODE);
    }

    @Test(groups = "postNewGist", description = "Check expected Description of gist equals to the posted after POST execution.")
    public void checkPostedGistDescription() {
        apiLogger.debug("Posted gist id = " + postedGist.getId());
        Assert.assertEquals(postingGist.getDescription(), postedGist.getDescription(), "Description of new posted gist does not equal to the expected.");
    }

    @Test(groups = "patchGist", description = "")
    public void checkPatchStatusCode() {
        apiLogger.info("Response status is: " + patchResponse.getStatusLine());
        Assert.assertEquals(PATCH_SUCCESS_CODE, patchResponse.getStatusLine().getStatusCode(), "Status code does not equal " + PATCH_SUCCESS_CODE);
    }

    @Test(groups = "patchGist", description = "")
    public void checkPatchedGistIdCorrect() {
        apiLogger.debug("Patched gist id: " + updatedGist.getId());
        Assert.assertEquals(updatedGist.getId(), GistUtils.getCreatedGistIdFromFile(), "Unexpected gist updated.");
    }

    @Test(groups = "patchGist", description = "")
    public void checkHistoryAfterPatch() {
        for (int i = 0; i < updatedGist.getHistory().length; i++) {
            apiLogger.debug("Patched gist files history: " + updatedGist.getHistory()[i].toString());
            apiLogger.debug("Patched gist files history: " + updatedGist.getHistory()[i].getChange_status().toString());
        }

        Assert.assertEquals(updatedGist.getHistory().length, 2, "History must have 2 records after created and patch.");
    }

    @Test(groups = "patchGist", description = "")
    public void checkFilesCountAfterUpdate() {
        apiLogger.debug("Patched gist files: " + updatedGist.getFiles().toString());
        Assert.assertEquals(2, updatedGist.getFiles().keySet().size(), "Files count must be 2 after update.");
    }


   /* @Test(priority = 3)
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

    }*/
}

