package com.epam.transavia.demo.tests.features.api;

import com.epam.transavia.demo.core.exceptions.BufferedReaderException;
import com.epam.transavia.demo.core.exceptions.HttpClientException;
import com.epam.transavia.demo.tests.features.api.bo.Gist;
import com.epam.transavia.demo.tests.features.api.bo.HttpRequestType;
import com.google.gson.*;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class GitService {
    private static final Logger apiLogger = LogManager.getLogger("ApiTests");
    private static final String GIST_ID_FILE_PATH = "./target/gistId.txt";


    static HttpRequestBase getHttpTokenAuthorization(String uri, HttpRequestType type) {
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

    static CloseableHttpResponse executeResponse(CloseableHttpClient httpClient, HttpRequestBase httpRequest) {
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

    static void closeResponse(CloseableHttpResponse response) {
        try {
            response.close();
        } catch (IOException e) {
            apiLogger.error("Cannot close the response.");
        }
    }

    static StringEntity createJSONEntityFromObject(Object object) {
        Gson gson = new GsonBuilder().create();
        StringEntity postJson = null;
        try {
            postJson = new StringEntity(gson.toJson(object));
        } catch (UnsupportedEncodingException e) {
            apiLogger.error("Cannot convert object to JSON StringEntity: " + e.getMessage());
        }
        if (postJson != null) {
            postJson.setContentType("application/json");
        }
        return postJson;
    }

    static Gist createNewGist() {
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
    static Gist createGistToUpdateFileContentAndNewFile() {
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

    static List<Gist> convertArrayGistsFromJsonResponseToPOJOList(HttpResponse response) {
        Gson gson = new Gson();
        List<Gist> gists = new ArrayList<Gist>();
        JsonArray jsonArray = new JsonParser().parse(getBufferedReader(response)).getAsJsonArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            Gist gist = gson.fromJson(jsonArray.get(i).getAsJsonObject(), Gist.class);
            gists.add(gist);
        }
        return gists;
    }

    static Gist convertGistFromJsonResponseToPOJO(HttpResponse response) {

        JsonObject jsonObject = new JsonParser().parse(getBufferedReader(response)).getAsJsonObject();
        return new Gson().fromJson(jsonObject, Gist.class);
    }

    private static BufferedReader getBufferedReader(HttpResponse response) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));
        } catch (IOException e) {
            apiLogger.error("Cannot put entity content to BufferedReader: " + e.getMessage());
            throw new BufferedReaderException("Cannot put entity content to BufferedReader: " + e.getMessage());
        }
        return br;
    }

    static String getCreatedGistIdFromFile() {
        String gistIdToUpdate = "";
        try {
            gistIdToUpdate = FileUtils.readLines(new File(GIST_ID_FILE_PATH), "utf-8").get(0);
        } catch (IOException e) {
            apiLogger.error("Did not get GistId from file " + e.getMessage());
        }
        return gistIdToUpdate;
    }

    static void saveGistIdToFile(Gist resultGist) {
        try {
            FileUtils.write(new File(GIST_ID_FILE_PATH), resultGist.getId(), "utf-8");
        } catch (IOException e) {
            apiLogger.error("Failed to save GistId to file " + e.getMessage());
        }

    }

    static String getGistIdFilePath() {
        return GIST_ID_FILE_PATH;
    }
}
