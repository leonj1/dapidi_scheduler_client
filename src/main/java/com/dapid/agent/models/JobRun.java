package com.dapid.agent.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.Instant;
import java.util.UUID;

import static com.github.choonchernlim.betterPreconditions.preconditions.PreconditionFactory.expect;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public class JobRun {
    private UUID id;
    private UUID jobId;
    private RunState runState;
    private Integer exitCode;
    private Instant startTime;
    private Instant endTime;

    public JobRun(UUID jobId, RunState runState) {
        this.jobId = jobId;
        this.runState = runState;
    }

    public JobRun(JobInstance jobInstance) {
        expect(jobInstance, "jobInstance").not().toBeNull().check();
        this.id = jobInstance.getId();
        this.jobId = jobInstance.getJob().getId();
        this.runState = jobInstance.getRunState();
        this.exitCode = jobInstance.getExitCode();
        this.startTime = jobInstance.getStarted();
        this.endTime = jobInstance.getStopped();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getJobId() {
        return jobId;
    }

    public void setJobId(UUID jobId) {
        this.jobId = jobId;
    }

    public RunState getRunState() {
        return runState;
    }

    public void setRunState(RunState runState) {
        this.runState = runState;
    }

    public Integer getExitCode() {
        return exitCode;
    }

    public void setExitCode(Integer exitCode) {
        this.exitCode = exitCode;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        JobRun jobRun = (JobRun) o;

        return new EqualsBuilder()
                .append(id, jobRun.id)
                .append(jobId, jobRun.jobId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(jobId)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("jobId", jobId)
                .append("runState", runState)
                .append("exitCode", exitCode)
                .append("startTime", startTime)
                .append("endTime", endTime)
                .toString();
    }
}
