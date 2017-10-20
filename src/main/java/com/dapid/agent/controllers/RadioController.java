package com.dapid.agent.controllers;

import com.dapid.agent.controllers.routes.AddNewJobRoute;
import com.dapid.agent.controllers.routes.HealthCheckRoute;
import com.dapid.agent.controllers.routes.ListJobsRoute;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public class RadioController {
    private HealthCheckRoute healthCheckRoute;
    private ListJobsRoute listJobsRoute;
    private AddNewJobRoute runJobRoute;

    public RadioController(HealthCheckRoute healthCheckRoute, ListJobsRoute listJobsRoute, AddNewJobRoute runJobRoute) {
        this.healthCheckRoute = healthCheckRoute;
        this.listJobsRoute = listJobsRoute;
        this.runJobRoute = runJobRoute;
    }

    public void expose() {
        get("/health", this.healthCheckRoute);
        get("/jobs", this.listJobsRoute);
        post("/jobs", this.runJobRoute);
    }
}
