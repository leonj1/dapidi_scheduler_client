package com.dapid.agent.services;

import java.io.IOException;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public interface AppHttpClient {
    WebRestResponse registerSelf(String payload) throws IOException;
    WebRestResponse health() throws IOException;
}
