package com.epam.transavia.demo.tests.features.api.services;

import com.epam.transavia.demo.core.exceptions.HttpClientException;
import com.epam.transavia.demo.tests.features.api.bo.Gist;
import com.epam.transavia.demo.tests.features.api.bo.HttpRequestType;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class GistService {

    private static final Logger apiLogger = LogManager.getLogger("ApiTests");
    private CloseableHttpClient httpClient = HttpClients.createDefault();


    public CloseableHttpResponse getAuthorizedGetResponse(String uri) {
        HttpGet httpGet = (HttpGet) getHttpTokenAuthorization(uri, HttpRequestType.GET);
        return executeResponse(httpClient, httpGet);
    }

    public CloseableHttpResponse getAuthorizedPostResponse(String uri, StringEntity sentEntity) {
        HttpPost httpPost = (HttpPost) getHttpTokenAuthorization(uri, HttpRequestType.POST);
        httpPost.setEntity(sentEntity);
        return executeResponse(httpClient, httpPost);

    }

    public CloseableHttpResponse getAuthorizedPatchResponse(String uri, StringEntity sentEntity) {
        HttpPatch httpPatch = (HttpPatch) getHttpTokenAuthorization(uri, HttpRequestType.PATCH);
        httpPatch.setEntity(sentEntity);
        return executeResponse(httpClient, httpPatch);
    }

    public CloseableHttpResponse getAuthorizedPutResponse(String uri) {
        HttpPut httpPut = (HttpPut) getHttpTokenAuthorization(uri, HttpRequestType.PUT);
        return executeResponse(httpClient, httpPut);
    }

    public CloseableHttpResponse getAuthorizedDeleteResponse(String uri) {
        HttpDelete httpDelete = (HttpDelete) getHttpTokenAuthorization(uri, HttpRequestType.DELETE);
        return executeResponse(httpClient, httpDelete);
    }


   public HttpRequestBase getHttpTokenAuthorization(String uri, HttpRequestType type) {
        HttpRequestBase httpRequest;
        switch (type) {
            case GET: {
                httpRequest = new HttpGet(uri);
                break;
            }
            case POST: {
                httpRequest = new HttpPost(uri);
                break;
            }
            case PATCH: {
                httpRequest = new HttpPatch(uri);
                break;
            }
            case PUT: {
                httpRequest = new HttpPut(uri);
                break;
            }
            case DELETE: {
                httpRequest = new HttpDelete(uri);
                break;
            }
            default: {
                throw new HttpClientException("Unknown reguest type is specified " + type);
            }
        }
        httpRequest.setHeader("Authorization", System.getenv("OAUTH_TOKEN"));
        return httpRequest;
    }

   public CloseableHttpResponse executeResponse(CloseableHttpClient httpClient, HttpRequestBase httpRequest) {
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpRequest);
        } catch (IOException e) {
            apiLogger.error(MessageFormat.format("Execute request {1} failed ", httpRequest.getClass().getSimpleName())
                            + e.getMessage());
            throw new HttpClientException(MessageFormat.format("Execute request {1} failed ", httpRequest.getClass().getSimpleName())
                                          + e.getMessage());
        }
        return response;
    }

    public void closeResponse(CloseableHttpResponse response) {
        if (response != null) {
            try {
                response.close();
            } catch (IOException e) {
                apiLogger.error("Cannot close the response.");
            }
        }
    }



    public static Gist createNewGist() {
        Gist gist = new Gist();
        gist.setDescription("test gist description");
        gist.setIsPublic(true);
        gist.setFiles(createFileWithContent("fileTest.txt", "Test content!"));
        return gist;
    }

    /**
     * patch JSON must be of the following format:
     * { "description": "the description for this gist",
     * "files": {"file1.txt": { "content": "updated file contents"  },
     * "old_name.txt": { "filename": "new_name.txt", "content": "modified contents" },
     * "new_file.txt": {"content": "a new file"},"delete_this_file.txt": null  } }
     */
    public static Gist createGistUpdateFileContentAndNewFile() {
        Gist gist = new Gist();
        gist.setDescription("updated gist description");
        Map<String, String> newFileContent = new HashMap<String, String>() {{
            put("content", "New Updated File Content");
        }};
        Map<String, Map<String, String>> files = createFileWithContent("fileTest.txt", "Updated File Content!!!!");
        ;
        files.put("fileNew2.txt", newFileContent);
        gist.setFiles(files);
        return gist;
    }

    private static Map<String, Map<String, String>> createFileWithContent(String fileName, String content) {
        Map<String, String> fileContent = new HashMap<String, String>() {{
            put("content", content);
        }};
        Map<String, Map<String, String>> files = new HashMap<>();
        files.put(fileName, fileContent);
        return files;
    }

}
