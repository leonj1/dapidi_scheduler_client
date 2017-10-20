package com.dapid.agent.context;

import com.dapid.agent.models.JobDefinition;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.UUID;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public class AddJobContext {
    private UUID jobInstanceId;
    private JobDefinition jobDefinition;

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

    // getters and setters

    public UUID getJobInstanceId() {
        return jobInstanceId;
    }

    public void setJobInstanceId(UUID jobInstanceId) {
        this.jobInstanceId = jobInstanceId;
    }

    public JobDefinition getJobDefinition() {
        return jobDefinition;
    }

    public void setJobDefinition(JobDefinition jobDefinition) {
        this.jobDefinition = jobDefinition;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("jobInstanceId", jobInstanceId)
                .append("jobDefinition", jobDefinition)
                .toString();
    }
}
