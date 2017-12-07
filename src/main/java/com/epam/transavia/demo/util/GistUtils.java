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
    private static final String GIST_ID_FILE_PATH = "./target/gistId.txt";
    //private static final String GIST_ID_FILE_PATH = "/gistId.txt";
    private static final String ENCODING = "utf-8";
    private static final String CONTENT_TYPE = "application/json";

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
            postJson.setContentType(CONTENT_TYPE);
        }
        return postJson;
    }

    public static List<Gist> convertJsonGistsArrayToGistsList(HttpResponse response) {
        Gson gson = new Gson();
        List<Gist> gists = new ArrayList<Gist>();

        BufferedReader br = getBufferedReader(response);
        JsonArray jsonArray = new JsonParser().parse(br).getAsJsonArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            Gist gist = gson.fromJson(jsonArray.get(i).getAsJsonObject(), Gist.class);
            gists.add(gist);
        }
        closeBufferedReader(br);
        return gists;
    }



    public static Gist convertJsonResponseToGist(HttpResponse response) {
        BufferedReader br = getBufferedReader(response);
        JsonObject jsonObject = new JsonParser().parse(br).getAsJsonObject();
        closeBufferedReader(br);

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

    private static void closeBufferedReader(BufferedReader br) {
        try {
            br.close();
        } catch (IOException e) {
            LogManager.getLogger(GistUtils.class.getSimpleName()).error("Could not close the buffered reader stream " + e.getMessage());;
        }
    }


    public static String getCreatedGistIdFromFile() {
        String gistIdToUpdate = "";
        try {
            gistIdToUpdate = FileUtils.readLines(new File(GIST_ID_FILE_PATH), ENCODING).get(0);
        } catch (IOException | IndexOutOfBoundsException e) {
            LogManager.getLogger(GistUtils.class.getSimpleName()).error("Did not get GistId from file " + e.getMessage());
        }
        return gistIdToUpdate;
    }

    public static void saveGistIdToFile(Gist resultGist) {
        if( resultGist.getId() != null){
            try {
                FileUtils.write(new File(GIST_ID_FILE_PATH), resultGist.getId(), ENCODING);
            } catch (IOException e) {
                LogManager.getLogger(GistUtils.class.getSimpleName()).error("Failed to save GistId to file " + e.getMessage());
            }
        }
    }

    public static String getGistIdFilePath() {
        return GIST_ID_FILE_PATH;
    }
}
