package com.dapid.agent.controllers.routes;

import com.dapid.agent.models.Jobs;
import com.dapid.agent.services.SimpleExitRoute;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public class ListJobByJobInstanceIdRoute implements Route {
    private ConcurrentHashMap<UUID, Jobs> map;

    public ListJobByJobInstanceIdRoute(ConcurrentHashMap<UUID, Jobs> map) {
        this.map = map;
    }

    private String execute(Response res, UUID jobInstanceUuid) {
        if (this.map.contains(jobInstanceUuid)) {
            return SimpleExitRoute.builder(res).OK_200().json(
                    this.map.get(jobInstanceUuid),
                    Jobs.class
            );
        }
        return SimpleExitRoute.builder(res).NOT_FOUND_404().text("not found");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        return execute(response, UUID.fromString(request.params("uuid")));
    }
}
