package com.dapid.agent.models;

import java.util.UUID;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public class JobRunResult {
    private UUID jobId;
    private int exitCode;

    public JobRunResult(UUID jobId, int exitCode) {
        this.jobId = jobId;
        this.exitCode = exitCode;
    }
}
