package com.dapid.agent.models;

import com.dapid.agent.context.AddJobContext;
import com.dapid.agent.services.Process;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.UUID;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public class Job {
    private UUID id;
    private String command;
    private String name;
    private String runAs;
    private String userProfile;
    private Integer retryOnFailure;
    private String stdoutFile;
    private String stderrFile;
    private Integer maxRunTime;
    private Integer maxRetry;
    private Process process;

    public Job(AddJobContext context, Process process) {
        this(context);
        this.process = process;
    }

    public Job(AddJobContext context) {
        this.id = context.getJobInstanceId();
        this.name = context.getJobDefinition().getName();
        this.command = context.getJobDefinition().getCommand();
        this.runAs = context.getJobDefinition().getRunAs();
        this.userProfile = context.getJobDefinition().getUserProfile();
        this.retryOnFailure = context.getJobDefinition().getRetryOnFailure();
        this.stdoutFile = context.getJobDefinition().getStdoutFile();
        this.stderrFile = context.getJobDefinition().getStderrFile();
        this.maxRunTime = context.getJobDefinition().getMaxRunTime();
        this.maxRetry = context.getJobDefinition().getMaxRetry();
    }

    public Process command() {
        return this.process;
    }

    public String getName() {
        return name;
    }

    public Integer getMaxRetry() {
        return maxRetry;
    }

    public Integer getMaxRunTime() {
        return maxRunTime;
    }

    public UUID getId() {
        return id;
    }

    public String getCommand() {
        return command;
    }

    public String getRunAs() {
        return runAs;
    }

    public String getUserProfile() {
        return userProfile;
    }

    public Integer getRetryOnFailure() {
        return retryOnFailure;
    }

    public String getStdoutFile() {
        return stdoutFile;
    }

    public String getStderrFile() {
        return stderrFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Job job = (Job) o;

        return new EqualsBuilder()
                .append(id, job.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("command", command)
                .append("runAs", runAs)
                .append("userProfile", userProfile)
                .append("retryOnFailure", retryOnFailure)
                .append("stdoutFile", stdoutFile)
                .append("stderrFile", stderrFile)
                .toString();
    }
}
