package com.epam.transavia.demo.util;

import com.epam.transavia.demo.core.exceptions.BufferedReaderException;
import com.epam.transavia.demo.tests.features.api.bo.Gist;
import com.google.gson.*;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.logging.log4j.LogManager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class GistUtils {
    //Path for local run
   // private static final String GIST_ID_FILE_PATH = "./target/gistId.txt";
    private static final String GIST_ID_FILE_PATH = "/gistId.txt";

    public static StringEntity convertObjToJsonStringEntity(Object object) {
        Gson gson = new GsonBuilder().create();
        StringEntity postJson = null;
        try {
            postJson = new StringEntity(gson.toJson(object));
        } catch (UnsupportedEncodingException e) {
            LogManager.getLogger(GistUtils.class.getSimpleName())
                    .error("Cannot convert object to JSON StringEntity: " + e.getMessage());
        }
        if (postJson != null) {
            postJson.setContentType("application/json");
        }
        return postJson;
    }

    public static List<Gist> convertArrayGistsFromJsonResponseToPOJOList(HttpResponse response) {
        Gson gson = new Gson();
        List<Gist> gists = new ArrayList<Gist>();
        JsonArray jsonArray = new JsonParser().parse(getBufferedReader(response)).getAsJsonArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            Gist gist = gson.fromJson(jsonArray.get(i).getAsJsonObject(), Gist.class);
            gists.add(gist);
        }
        return gists;
    }

    public static Gist convertJsonResponseToGist(HttpResponse response) {

        JsonObject jsonObject = new JsonParser().parse(getBufferedReader(response)).getAsJsonObject();
        return new Gson().fromJson(jsonObject, Gist.class);
    }

    private static BufferedReader getBufferedReader(HttpResponse response) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));
        } catch (IOException e) {
            throw new BufferedReaderException("Cannot put entity content to BufferedReader: " + e.getMessage());
        }
        return br;
    }

    public static String getCreatedGistIdFromFile() {
        String gistIdToUpdate = "";
        try {
            gistIdToUpdate = FileUtils.readLines(new File(GIST_ID_FILE_PATH), "utf-8").get(0);
        } catch (IOException e) {
            LogManager.getLogger(GistUtils.class.getSimpleName()).error("Did not get GistId from file " + e.getMessage());
        }
        return gistIdToUpdate;
    }

    public static void saveGistIdToFile(Gist resultGist) {
        try {
            FileUtils.write(new File(GIST_ID_FILE_PATH), resultGist.getId(), "utf-8");
        } catch (IOException e) {
            LogManager.getLogger(GistUtils.class.getSimpleName()).error("Failed to save GistId to file " + e.getMessage());
        }

    }

    public static String getGistIdFilePath() {
        return GIST_ID_FILE_PATH;
    }
}
