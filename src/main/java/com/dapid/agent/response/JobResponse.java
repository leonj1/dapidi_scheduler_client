package com.dapid.agent.response;

import com.dapid.agent.models.JobInstance;
import com.dapid.agent.models.JobRun;
import com.dapid.agent.models.JobState;
import com.dapid.agent.models.Jobs;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static com.github.choonchernlim.betterPreconditions.preconditions.PreconditionFactory.expect;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public class JobResponse {
    private UUID jobId;
    private JobState jobState;
    private List<JobRun> jobRuns;

    public JobResponse(Jobs jobs) {
        expect(jobs, "job").not().toBeNull().check();
        this.jobId = jobs.getJob().getId();
        // TODO Find every instantiation of Jobs and ensure State is set
        this.jobState = jobs.getState();
        this.jobRuns = Lists.newArrayList();

        // lets also determine the overall job status from the latest jobRun
        Instant latestRun = Instant.MIN;
        for(UUID id : jobs.getJobInstances().keySet()) {
            JobInstance ji = jobs.getJobInstances().get(id);
            this.jobRuns.add(new JobRun(ji));
            // Source: https://docs.oracle.com/javase/8/docs/api/java/time/Instant.html#compareTo-java.time.Instant-
            if (ji.getStopped().compareTo(latestRun) > 0) {
                this.jobState = ji.getJobState();
            }
        }
    }

    public JobState getJobState() {
        return jobState;
    }

    public UUID getJobId() {
        return jobId;
    }

    public void setJobId(UUID jobId) {
        this.jobId = jobId;
    }

    public List<JobRun> getJobRuns() {
        return jobRuns;
    }

    public void setJobRuns(List<JobRun> jobRuns) {
        this.jobRuns = jobRuns;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        JobResponse that = (JobResponse) o;

        return new EqualsBuilder()
                .append(jobId, that.jobId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(jobId)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("jobId", jobId)
                .append("jobRuns", jobRuns)
                .toString();
    }
}
