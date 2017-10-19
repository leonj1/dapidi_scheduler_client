package com.dapid.agent.configs;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public interface Props {
    Integer getFailedJobRetryInterval();
    Integer getHealthCheckInterval();
    String getServerProtocol();
    String getJobInstanceApi();
    String getClientHost();
    String getClientPort();
    String getServerHost();
    String getServerPort();

    class Fake implements Props {

        @Override
        public Integer getFailedJobRetryInterval() {
            return null;
        }

        @Override
        public Integer getHealthCheckInterval() {
            return null;
        }

        @Override
        public String getServerProtocol() {
            return null;
        }

        @Override
        public String getJobInstanceApi() {
            return null;
        }

        @Override
        public String getClientHost() {
            return null;
        }

        @Override
        public String getClientPort() {
            return null;
        }

        @Override
        public String getServerHost() {
            return null;
        }

        @Override
        public String getServerPort() {
            return null;
        }
    }
}
