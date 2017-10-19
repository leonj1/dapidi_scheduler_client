package com.dapid.agent.controllers.routes;

import com.dapid.agent.models.Jobs;
import com.google.gson.Gson;
import org.eclipse.jetty.http.HttpStatus;
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
public class ListJobsRoute implements Route, ExitRoute {
    private static final Logger log = LoggerFactory.getLogger(ListJobsRoute.class);

    private Gson gson;
    private ConcurrentHashMap<UUID, Jobs> map;

    public ListJobsRoute(ConcurrentHashMap<java.util.UUID, Jobs> map) {
        this.gson = new Gson();
        this.map = map;
    }

    private String execute(Response res) {
        String json = this.gson.toJson(new ArrayList<Jobs>(map.values()));
        log.info(String.format("Listing jobs we are working on:\n%s", json));
        return exit(res, HttpStatus.OK_200, json, null);
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        return execute(response);
    }
}
