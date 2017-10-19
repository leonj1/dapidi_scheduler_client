package com.dapid.agent.controllers;

import com.dapid.agent.controllers.routes.HealthCheckRoute;
import com.dapid.agent.controllers.routes.ListJobsRoute;
import com.dapid.agent.controllers.routes.RunJobRoute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public class RadioController {
    private static final Logger log = LoggerFactory.getLogger(RadioController.class);

    private HealthCheckRoute healthCheckRoute;
    private ListJobsRoute listJobsRoute;
    private RunJobRoute runJobRoute;

    public RadioController(HealthCheckRoute healthCheckRoute, ListJobsRoute listJobsRoute, RunJobRoute runJobRoute) {
        this.healthCheckRoute = healthCheckRoute;
        this.listJobsRoute = listJobsRoute;
        this.runJobRoute = runJobRoute;
        log.info("RadioController initialized");
    }

    public void expose() {
        get("/health", this.healthCheckRoute);
        get("/jobs", this.listJobsRoute);
        post("/jobs", this.runJobRoute);
    }
}
