package com.dapid.agent.configs;


import com.josemleon.AppProperty;
import com.josemleon.exceptions.PropertiesFileNotFoundException;

import java.io.IOException;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2016
 **/
public class AppProperties {

    private AppProperty getProperty;

    public AppProperties(AppProperty getProperty) {
        this.getProperty = getProperty;
    }

    public Integer getFailedJobRetryInterval() throws PropertiesFileNotFoundException, IOException {
        return Integer.parseInt(this.getProperty.value("failed.job.retry.interval.seconds"));
    }

    public Integer getHealthCheckInterval() throws PropertiesFileNotFoundException, IOException {
        return Integer.parseInt(this.getProperty.value("healthcheck.interval.seconds"));
    }

    public String getServerProtocol() throws PropertiesFileNotFoundException, IOException {
        return this.getProperty.value("server.protocol");
    }

    public String getJobInstanceApi() throws PropertiesFileNotFoundException, IOException {
        return this.getProperty.value("update.job.instance");
    }

    public String getClientHost() throws PropertiesFileNotFoundException, IOException {
        return this.getProperty.value("client.host");
    }

    public String getClientPort() throws PropertiesFileNotFoundException, IOException {
        return this.getProperty.value("client.port");
    }

    public String getServerHost() throws PropertiesFileNotFoundException, IOException {
        return this.getProperty.value("server.host");
    }

    public String getServerPort() throws PropertiesFileNotFoundException, IOException {
        return this.getProperty.value("server.port");
    }

    public int httpServerPort() throws PropertiesFileNotFoundException, IOException {
        return Integer.parseInt(this.getProperty.value("http.server.port"));
    }
}
