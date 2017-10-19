package com.dapid.agent.configs;

import com.dapid.agent.services.GetProperty;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2016
 **/
public class AppProperties implements Props {

    private GetProperty getProperty;

    public AppProperties(GetProperty getProperty) {
        this.getProperty = getProperty;
    }

    public Integer getFailedJobRetryInterval() {
        return Integer.parseInt(this.getProperty.value("failed.job.retry.interval.seconds"));
    }

    public Integer getHealthCheckInterval() {
        return Integer.parseInt(this.getProperty.value("healthcheck.interval.seconds"));
    }

    public String getServerProtocol() {
        return this.getProperty.value("server.protocol");
    }

    public String getJobInstanceApi() {
        return this.getProperty.value("update.job.instance");
    }

    public String getClientHost() {
        return this.getProperty.value("client.host");
    }

    public String getClientPort() {
        return this.getProperty.value("client.port");
    }

    public String getServerHost() {
        return this.getProperty.value("server.host");
    }

    public String getServerPort() {
        return this.getProperty.value("server.port");
    }
}
