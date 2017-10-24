package com.dapid.agent.services;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * This class represents the HttpClient for this application.
 * In the future, we may need to have additional HttpClients, one per backend microservice, but not needed now.
 *
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public class AgentAppHttpClient implements AppHttpClient {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private String serverBaseUrl;
    private String serverHealthUrl;
    private String registerUrl;

    public AgentAppHttpClient(String serverBaseUrl, String serverHealthUrl, String registerUrl) {
        this.serverBaseUrl = serverBaseUrl;
        this.serverHealthUrl = serverHealthUrl;
        this.registerUrl = registerUrl;
    }

    @Override
    public WebRestResponse registerSelf(String payload) throws IOException {
        return post(
                new BaseUrl(this.serverBaseUrl)
                        .append(this.registerUrl),
                payload
        );
    }

    @Override
    public WebRestResponse health() throws IOException {
        return null;
    }

    // privates

    private WebRestResponse get(String urlResource) throws IOException {
        log.debug(String.format("Getting from: %s", urlResource));
        HttpClient client = HttpClients.custom().build();
        HttpUriRequest request = RequestBuilder.get()
                .setUri(urlResource)
                .setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
                .build();
        HttpResponse response = client.execute(request);
        return new WebRestResponse(
                response.getStatusLine().getStatusCode(),
                EntityUtils.toString(
                        response.getEntity(),
                        StandardCharsets.UTF_8.name()
                )
        );
    }

    private HttpResponse post(String urlResource, Map.Entry<String, String> header, String payload) throws IOException {
        log.debug(String.format("Posting to: %s", urlResource));
        HttpClient client = HttpClients.custom().build();
        HttpUriRequest request = RequestBuilder.post()
                .setUri(urlResource)
                .setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
                .setHeader(header.getKey(), header.getValue())
                .setEntity(
                        new StringEntity(
                                payload,
                                ContentType.APPLICATION_FORM_URLENCODED
                        )
                )
                .build();
        return client.execute(request);
    }

    private WebRestResponse post(String urlResource, String payload) throws IOException {
        log.debug(String.format("Posting to: %s", urlResource));
        HttpPost request = new HttpPost(urlResource);
        request.setEntity(
                new StringEntity(
                        payload,
                        ContentType.APPLICATION_FORM_URLENCODED
                )
        );
        HttpResponse response = HttpClientBuilder
                .create()
                .build()
                .execute(request);
        return new WebRestResponse(
                response.getStatusLine().getStatusCode(),
                EntityUtils.toString(
                        response.getEntity(),
                        StandardCharsets.UTF_8.name()
                )
        );
    }

    private WebRestResponse delete(String urlResource, String payload) throws IOException {
        log.debug("Deleting from %s", urlResource);
        MyHttpDelete request = new MyHttpDelete(urlResource);
        request.setEntity(
                new StringEntity(
                        payload,
                        ContentType.APPLICATION_FORM_URLENCODED
                )
        );
        HttpResponse response = HttpClientBuilder
                .create()
                .build()
                .execute(request);
        return new WebRestResponse(
                response.getStatusLine().getStatusCode(),
                EntityUtils.toString(
                        response.getEntity(),
                        StandardCharsets.UTF_8.name()
                )
        );
    }

    /**
     * Helper class attempting to reduce repetitive String.format code
     */
    class BaseUrl {
        private String baseUrl;

        BaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        String append(String resource) {
            return String.format("%s/%s", this.baseUrl, resource);
        }
    }
}
