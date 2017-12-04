package com.epam.transavia.demo.tests.features.api;

import com.epam.transavia.demo.tests.features.api.bo.Gist;
import org.apache.http.impl.client.CloseableHttpClient;

import java.util.HashMap;
import java.util.Map;

public class GitHubService {

    public CloseableHttpClient getHttpClientWithTokenAuthorization(String navigationURI) {
        CloseableHttpClient httpClient = null;
        return httpClient;
    }

    public static Gist createTestGist() {
        Gist gist = new Gist();
        gist.setDescription("test gist description");
        gist.setIsPublic(true);

        Map<String, Map<String ,String>> sendGist = new HashMap<>();
        Map<String, String> fileContent = new HashMap<>();
        fileContent.put("content", "Test content!");
        sendGist.put("fileTest.txt", fileContent);

        gist.setFiles(sendGist);
        return gist;
    }
}
