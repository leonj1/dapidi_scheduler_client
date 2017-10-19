package com.dapid.agent.services;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;

import java.io.IOException;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public class HttpPhoneHome implements PhoneHome {
    private HttpClient httpClient;

    public HttpPhoneHome(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public HttpResponse call(HttpPut httpPut) throws IOException {
        return this.httpClient.execute(httpPut);
    }
}
