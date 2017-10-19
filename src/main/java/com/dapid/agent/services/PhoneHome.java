package com.dapid.agent.services;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;

import java.io.IOException;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public interface PhoneHome {
    HttpResponse call(HttpPut httpPut) throws IOException;

    class Fake implements PhoneHome {

        @Override
        public HttpResponse call(HttpPut httpPut) throws IOException {
            return null;
        }
    }
}
