package com.dapid.agent.context;

import com.dapid.agent.models.JobDefinition;

import java.util.UUID;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public class AddJobContext {
    @JsonRequired public UUID jobInstanceId;
    @JsonRequired public JobDefinition jobDefinition;

    public AddJobContext() {
    }

    public AddJobContext(UUID jobInstanceId, JobDefinition jobDefinition) {
        this.jobInstanceId = jobInstanceId;
        this.jobDefinition = jobDefinition;
    }
    
    public boolean valid() {
        return (this.jobInstanceId != null &&
                this.jobDefinition != null &&
                this.jobDefinition.getId() != null &&
                this.jobDefinition.getCommand() != null &&
                this.jobDefinition.getRetryOnFailure() != null &&
                this.jobDefinition.getStdoutFile() != null &&
                this.jobDefinition.getStderrFile() != null &&
                this.jobDefinition.getRunAs() != null);
    }
}
