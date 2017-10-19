package com.dapid.agent.models;

import com.dapid.agent.response.JobResponse;
import com.dapid.agent.services.PhoneHome;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.HashMap;
import java.util.UUID;

import static com.github.choonchernlim.betterPreconditions.preconditions.PreconditionFactory.expect;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public class Jobs {
    private static final Logger log = LoggerFactory.getLogger(Jobs.class);

    private Job job;
    private Integer latestExitCode;
    private JobState state;
    private HashMap<UUID, JobInstance> jobInstances;
    private String serverHost;
    private String serverPort;
    private String instanceApi;
    private PhoneHome httpClient;
    private Gson gson;

    public Jobs(Job job, String serverHost, String serverPort, String instanceApi, PhoneHome httpClient) {
        expect(job, "job").not().toBeNull().check();
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.instanceApi = instanceApi;
        this.job = job;
        this.httpClient = httpClient;
        this.jobInstances = Maps.newHashMap();
        this.latestExitCode = 999;
        this.state = JobState.INACTIVE;
        this.gson = new Gson();
    }

    public Jobs(Jobs jobs) {
        expect(jobs, "jobs").not().toBeNull().check();
        this.serverHost = jobs.serverHost;
        this.serverPort = jobs.serverPort;
        this.instanceApi = jobs.instanceApi;
        this.job = jobs.getJob();
        this.httpClient = jobs.httpClient;
        this.latestExitCode = jobs.getLatestExitCode();
        this.jobInstances = jobs.getJobInstances();
        this.state = jobs.state;
        this.gson = new Gson();
    }

    public Jobs run() {
        // bail if already running
        if (this.running()) {
            log.debug("Terminating executing since we're already running");
            return new Jobs(this);
        }

        HttpPut httpPut = new HttpPut(
                String.format(
                        "http://%s:%s/%s/%s",
                        this.serverHost,
                        this.serverPort,
                        this.instanceApi,
                        this.job.getId()
                )
        );
        Jobs jobs = new Jobs(this);
        JobResponse jobResponse = new JobResponse(jobs);

        // if already ran to success before, attempt to phone home
        if (this.completed()) {
            if (postHome(httpPut, jobResponse)) {
                log.info("We already completed running before, so phoned home to let them know. Terminating");
                return null;
            } else {
                log.info("Home not picking up. Caching and terminating");
                return jobs;
            }
        }

        // is not running, and has not previously run to completion
        UUID id = UUID.randomUUID();
        this.jobInstances.put(
                id,
                new JobInstance(
                    id,
                    this.job,
                    RunState.RUNNING,
                    Instant.now()
                )
        );
        JobInstance ji = null;
        ji = this.jobInstances.get(id);
        try {
            log.debug("Running command");
            ji.setExitCode(this.job.command().start());
            ji = this.jobInstances.get(id);
            ji.setRunState(RunState.SUCCESS);

            if (postHome(httpPut, jobResponse)) {
                log.info("Just completed running, so phoned home to let them know. Terminating");
                return null;
            } else {
                log.info("Bah! Home not picking up. Caching and terminating");
                return new Jobs(this);
            }
        } catch (IOException|InterruptedException e) {
            log.error(String.format("Job %s Error %s", id, e.getMessage()));
            if (ji != null) {
                ji.setRunState(RunState.FAILED);
                ji.setExitCode(800);
            }
        }
        return new Jobs(this);
    }

    private boolean completed() {
        // determine if ran successfully
        if (this.getLatestExitCode() == 0) {
            return true;
        }
        // also include if max num of retries has been reached
        return (this.job.getMaxRetry() >= this.getJobInstances().size());
    }

    private boolean running() {
        for(UUID id : this.jobInstances.keySet()) {
            JobInstance jobInstance = this.jobInstances.get(id);
            if (RunState.RUNNING.equals(jobInstance.getRunState()) ||
                    RunState.STARTED.equals(jobInstance.getRunState())) {
                return true;
            }
        }
        return false;
    }

    private boolean postHome(HttpPut httpPut, JobResponse jobResponse) {
        String json = this.gson.toJson(jobResponse);
        StringEntity input = null;
        try {
            input = new StringEntity(json);
        } catch (UnsupportedEncodingException e) {
            log.error(String.format("Unexpected error happened trying to serialize json. Error: %s", e.getMessage()));
            return false;
        }
        input.setContentType("application/json");
        httpPut.setEntity(input);

        HttpResponse response = null;
        try {
            response = this.httpClient.call(httpPut);
        } catch (IOException e) {
            log.error(String.format("Unexpected problem phoning home. Error: %s", e.getMessage()));
            return false;
        }

        if (response.getStatusLine().getStatusCode() != HttpStatus.OK_200) {
            log.error(String.format("Unexpected problem phoning home. HTTP Status code: %s", response.getStatusLine().getStatusCode()));
            return false;
        }
        return true;
    }

    public JobState getState() {
        return state;
    }

    public void setLatestExitCode(Integer latestExitCode) {
        this.latestExitCode = latestExitCode;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public HashMap<UUID, JobInstance> getJobInstances() {
        return jobInstances;
    }

    public Integer getLatestExitCode() {
        return latestExitCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Jobs jobs = (Jobs) o;

        return new EqualsBuilder()
                .append(job, jobs.job)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(job)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("job", job)
                .toString();
    }
}
