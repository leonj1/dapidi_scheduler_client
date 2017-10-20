package com.dapid.agent.controllers.routes;

import com.dapid.agent.models.Jobs;
import com.dapid.agent.services.SimpleExitRoute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public class ListJobsRoute implements Route {
    private static final Logger log = LoggerFactory.getLogger(ListJobsRoute.class);
    private ConcurrentHashMap<UUID, Jobs> map;

    public ListJobsRoute(ConcurrentHashMap<UUID, Jobs> map) {
        this.map = map;
    }

    private String execute(Response res) {
        return SimpleExitRoute.builder(res).OK_200().json(map.values(), ArrayList.class);
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        return execute(response);
    }
}
