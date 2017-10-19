package com.dapid.agent.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.Instant;
import java.util.UUID;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public class JobInstance {
    private UUID id;
    private Job job;
    volatile private JobState jobState;
    volatile private RunState runState;
    private Instant started;
    private Instant stopped;
    private Integer exitCode;

    public JobInstance(Job job) {
        this.job = job;
        this.id = UUID.randomUUID();
    }

    public JobInstance(UUID id, Job job, RunState runState, Instant startTime) {
        this.id = id;
        this.job = job;
        this.runState = runState;
        this.started = startTime;
    }

    public UUID getId() {
        return id;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public JobState getJobState() {
        return jobState;
    }

    public void setJobState(JobState jobState) {
        this.jobState = jobState;
    }

    public RunState getRunState() {
        return runState;
    }

    public void setRunState(RunState runState) {
        this.runState = runState;
    }

    public Instant getStarted() {
        return started;
    }

    public void setStarted(Instant started) {
        this.started = started;
    }

    public Instant getStopped() {
        return stopped;
    }

    public void setStopped(Instant stopped) {
        this.stopped = stopped;
    }

    public Integer getExitCode() {
        return exitCode;
    }

    public void setExitCode(Integer exitCode) {
        this.exitCode = exitCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        JobInstance that = (JobInstance) o;

        return new EqualsBuilder()
                .append(id, that.id)
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
                .append("job", job)
                .append("jobState", jobState)
                .append("runState", runState)
                .append("started", started)
                .append("stopped", stopped)
                .append("exitCode", exitCode)
                .toString();
    }
}
