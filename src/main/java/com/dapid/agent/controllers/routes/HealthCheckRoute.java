package com.dapid.agent.controllers.routes;

import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public class HealthCheckRoute implements Route, ExitRoute {

    @Override
    public Object handle(Request request, Response response) throws Exception {
        return exit(response, HttpStatus.OK_200, "OK", null);
    }
}
