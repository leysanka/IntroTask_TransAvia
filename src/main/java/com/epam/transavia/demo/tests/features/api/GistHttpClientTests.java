package com.epam.transavia.demo.tests.features.api;

import com.epam.transavia.demo.tests.features.api.bo.Gist;
import com.epam.transavia.demo.tests.features.api.bo.GistFactory;
import com.epam.transavia.demo.tests.features.api.services.GistService;
import com.epam.transavia.demo.util.GistUtils;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

public class GistHttpClientTests {

    private static final String BASE_URI = "https://api.github.com/gists";
    private static final Logger apiLogger = LogManager.getLogger("ApiTests");

    private static final String USER_LOGIN = "leysanka";
    private static final int POST_SUCCESS_CODE = 201;
    private static final int GET_SUCCESS_CODE = 200;
    private static final int PATCH_SUCCESS_CODE = 200;
    private static final int PUT_STAR_SUCCESS_CODE = 204;
    private static final int GET_STAR_SUCCESS_CODE = 204;
    private static final int DELETE_SUCCESS_CODE = 204;

    private static final String updateGistUriFormat = "%s/%s";
    private static final String starGistUriFormat = "%s/%s/star";


    private CloseableHttpResponse getGistsResponse, getStarResponse, postResponse, putResponse, patchResponse, deleteResponse;
    private List<Gist> baseGists;
    private Gist postingGist, postedGist, updateGist, updatedGist;

    private GistService service = new GistService();

    @BeforeGroups(groups = "getBaseUri", description = "Execute authorized GET to BASE_URI, fetch response and list of all gists of the user.")
    public void getBaseUriResponseAndGists() {
        getGistsResponse = service.getAuthorizedGetResponse(BASE_URI);
        baseGists = GistUtils.convertJsonGistsArrayToGistsList(getGistsResponse);
        service.closeResponse(getGistsResponse);
        apiLogger.debug("Before method called.");
    }

    @BeforeGroups("postNewGist")
    public void postNewGist() {
        postingGist = GistFactory.createNewGist();
        postResponse = service.getAuthorizedPostResponse(BASE_URI, GistUtils.convertObjToJsonStringEntity(postingGist));
        postedGist = GistUtils.convertJsonResponseToGist(postResponse);
        GistUtils.saveGistIdToFile(postedGist);
        service.closeResponse(postResponse);
        apiLogger.info("Before postGist method called.");
    }

    @BeforeGroups("patchGist")
    public void updateGistViaPatch() {
        String updateGistUri = String.format(updateGistUriFormat, BASE_URI, GistUtils.getCreatedGistIdFromFile());
        updateGist = GistFactory.createGistUpdateFileContentAndNewFile();
        patchResponse = service.getAuthorizedPatchResponse(updateGistUri, GistUtils.convertObjToJsonStringEntity(updateGist));
        updatedGist = GistUtils.convertJsonResponseToGist(patchResponse);
        service.closeResponse(patchResponse);
    }

    @BeforeGroups("starGist")
    public void starGistWithPut() {
        String starForGistUri = String.format(starGistUriFormat, BASE_URI, GistUtils.getCreatedGistIdFromFile());
        putResponse = service.getAuthorizedPutResponse(starForGistUri);
        service.closeResponse(putResponse);
        getStarResponse = service.getAuthorizedGetResponse(starForGistUri);
        service.closeResponse(getStarResponse);
    }

    @Test(groups = "getBaseUri", description = "Check Status Code of GET base uri is valid.")
    public void checkGetBaseUriStatusCode() {
        Assert.assertEquals(GET_SUCCESS_CODE, getGistsResponse.getStatusLine().getStatusCode(),
                "Status code of response in not " + GET_SUCCESS_CODE);
    }

    @Test(groups = "getBaseUri", description = "Check whether the expected user is authorized.")
    public void checkGetBaseUriAuthorizedUser() {
        apiLogger.info("Authorized user login is: " + baseGists.get(0).getOwner().getLogin());
        Assert.assertEquals(baseGists.get(0).getOwner().getLogin(), USER_LOGIN,
                "Authorized Owner login does not equal to the expected " + USER_LOGIN);
    }

    @Test(groups = "postNewGist", description = "Check status code is valid after POST execution")
    public void checkPostStatusCode() {
        apiLogger.info("Response status line: " + postResponse.getStatusLine());
        Assert.assertEquals(POST_SUCCESS_CODE, postResponse.getStatusLine().getStatusCode(), "Status code after new gist POST does not equal " + POST_SUCCESS_CODE);
    }

    @Test(groups = "postNewGist", description = "Check expected Description of gist equals to the posted after POST execution.")
    public void checkPostedGistDescription() {
        apiLogger.info("Posted gist id is: " + postedGist.getId());
        Assert.assertEquals(postingGist.getDescription(), postedGist.getDescription(), "Description of new posted gist does not equal to the expected.");
    }

    @Test(groups = "patchGist", dependsOnGroups = "postNewGist", description = "")
    public void checkPatchStatusCode() {
        apiLogger.info("Response status is: " + patchResponse.getStatusLine());
        Assert.assertEquals(PATCH_SUCCESS_CODE, patchResponse.getStatusLine().getStatusCode(), "Status code does not equal " + PATCH_SUCCESS_CODE);
    }

    @Test(groups = "patchGist", dependsOnGroups = "postNewGist", description = "")
    public void checkPatchedGistIdCorrect() {
        apiLogger.info("Patched gist id: " + updatedGist.getId());
        Assert.assertEquals(updatedGist.getId(), GistUtils.getCreatedGistIdFromFile(), "Unexpected gist updated.");
    }

    @Test(groups = "patchGist", dependsOnGroups = "postNewGist", description = "")
    public void checkHistoryAfterPatch() {
        for (int i = 0; i < updatedGist.getHistory().length; i++) {
            apiLogger.debug("Patched gist files history: " + updatedGist.getHistory()[i].toString());
            apiLogger.debug("Patched gist files history: " + updatedGist.getHistory()[i].getChange_status().toString());
        }
        Assert.assertEquals(updatedGist.getHistory().length, 2, "History must have 2 records after create and patch.");
    }

    @Test(groups = "patchGist", dependsOnGroups = "postNewGist", description = "")
    public void checkFilesCountAfterUpdate() {
        apiLogger.info("Patched gist files: " + updatedGist.getFiles().toString());
        Assert.assertEquals(2, updatedGist.getFiles().keySet().size(), "Files count must be 2 after update.");
    }

    @Test(groups = "starGist", dependsOnGroups = "postNewGist" )
    public void checkPutStarStatusCode() {
        apiLogger.info("Status line of PUT star for gist is: " + putResponse.getStatusLine());
        Assert.assertEquals(PUT_STAR_SUCCESS_CODE, putResponse.getStatusLine().getStatusCode(),
                "Star gist operation status code does not equal to the expected " + PUT_STAR_SUCCESS_CODE);
    }

    @Test(groups = "starGist", dependsOnGroups = "postNewGist")
    public void checkGistIsStarred() {
        apiLogger.info("Status line of GET starred gist is: " + getStarResponse.getStatusLine());
        Assert.assertEquals(GET_STAR_SUCCESS_CODE, getStarResponse.getStatusLine().getStatusCode(),
                "Get Starred gist status code does not equal to the expected " + GET_STAR_SUCCESS_CODE);
    }

    @Test(dependsOnGroups = "postNewGist", description = "DELETE posted gist and verify valid status code is returned after deletion.")
    public void deleteGistAndCheckStatusCode() {
        String gistIdToDeleteUri = String.format(updateGistUriFormat, BASE_URI, GistUtils.getCreatedGistIdFromFile());
        deleteResponse = service.getAuthorizedDeleteResponse(gistIdToDeleteUri);
        service.closeResponse(deleteResponse);

        apiLogger.info("Status line of DELETE response for gist id " + GistUtils.getCreatedGistIdFromFile()
                       + " is: " + deleteResponse.getStatusLine());

        Assert.assertEquals(DELETE_SUCCESS_CODE, deleteResponse.getStatusLine().getStatusCode(),
                "Status code of Delete does not equal to the expected " + DELETE_SUCCESS_CODE);

        FileUtils.deleteQuietly(new File(GistUtils.getGistIdFilePath()));
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        service.closeHttpClient();
        if (FileUtils.getFile(GistUtils.getGistIdFilePath()).exists()) {
            deleteGistAndCheckStatusCode();
        }
    }
}

