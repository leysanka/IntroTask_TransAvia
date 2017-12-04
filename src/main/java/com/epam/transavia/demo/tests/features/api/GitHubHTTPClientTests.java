package com.epam.transavia.demo.tests.features.api;

import com.epam.transavia.demo.tests.features.api.bo.Gist;
import com.google.gson.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class GitHubHTTPClientTests {

    private static final String BASE_URI = "https://api.github.com/gists";

    @Test
    public void setUpAuthorizedConnection() throws IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(BASE_URI);
        //httpGet.setHeader("Authorization", System.getProperty("OAUTH_TOKEN"));
        httpGet.setHeader("Authorization", "token 189e34550897e2a17c78228875170151119a7d64");
        CloseableHttpResponse response = httpclient.execute(httpGet);
        System.out.println(response.getStatusLine());
        HttpEntity entity1 = response.getEntity();
        Gson json = new Gson();
        BufferedReader br = new BufferedReader(
                new InputStreamReader((response.getEntity().getContent())));

        JsonObject jsonObj = new JsonParser().parse(br).getAsJsonArray().get(0).getAsJsonObject();

        Gist gist = json.fromJson(jsonObj, Gist.class);
        Map<String, Map<String, String>> gistFile = json.fromJson(jsonObj, Gist.class).getFiles();

        System.out.println(gist.toString());
        System.out.println(gistFile.values());

        EntityUtils.consume(entity1);
        response.close();

    }

    @Test
    public void createNewGist() {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(BASE_URI);
        httpPost.setHeader("Authorization", "token 189e34550897e2a17c78228875170151119a7d64");
        Gist gist = GitHubService.createTestGist();
        Gson gson = new GsonBuilder().create();
        try {
            StringEntity postJson = new StringEntity(gson.toJson(gist));
            postJson.setContentType("application/json");
            httpPost.setEntity(postJson);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            CloseableHttpResponse postResponse = httpclient.execute(httpPost);
            System.out.println(postResponse.getStatusLine());
            BufferedReader br = new BufferedReader(
                    new InputStreamReader((postResponse.getEntity().getContent())));

            JsonObject responseEntity = new JsonParser().parse(br).getAsJsonObject();
            Gist resultGist = new Gson().fromJson(responseEntity, Gist.class);
            System.out.println(resultGist.getId());
            postResponse.close();
        } catch (ClientProtocolException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
