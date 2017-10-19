package com.dapid.agent.services;

import java.io.IOException;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public interface Process {
    int start() throws IOException, InterruptedException;

    class FakeSuccessfulRun implements Process {

        @Override
        public int start() throws IOException, InterruptedException {
            Thread.sleep(2000);
            return 0;
        }
    }

    class FakeIOException implements Process {

        @Override
        public int start() throws IOException, InterruptedException {
            Thread.sleep(2000);
            throw new IOException("bad things happened");
        }
    }

    class FakeTenMinuteRunningProcess implements Process {

        @Override
        public int start() throws IOException, InterruptedException {
            Thread.sleep(60000);
            return 0;
        }
    }
}
