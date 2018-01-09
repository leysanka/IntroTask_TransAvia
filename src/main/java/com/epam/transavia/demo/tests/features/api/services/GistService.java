package com.epam.transavia.demo.tests.features.api.services;

import com.epam.transavia.demo.core.exceptions.HttpClientException;
import com.epam.transavia.demo.tests.features.api.bo.HttpRequestType;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.text.MessageFormat;

public class GistService {

    private static final Logger API_LOGGER = LogManager.getLogger("ApiTests");
    public static final String ENV_VARIABLE = "OAUTH_TOKEN";

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

    public void closeHttpClient() {
        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (IOException e) {
                API_LOGGER.error("Cannot close HTTP Client.");
            }
        }
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
                throw new HttpClientException("Unknown request type is specified " + type);
            }
        }
        httpRequest.setHeader("Authorization", System.getenv(ENV_VARIABLE));
        return httpRequest;
    }

   public CloseableHttpResponse executeResponse(CloseableHttpClient httpClient, HttpRequestBase httpRequest) {
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpRequest);
        } catch (IOException e) {
            API_LOGGER.error(MessageFormat.format("Execute request {1} failed ", httpRequest.getClass().getSimpleName())
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
                API_LOGGER.error("Cannot close the response.");
            }
        }
    }
}
