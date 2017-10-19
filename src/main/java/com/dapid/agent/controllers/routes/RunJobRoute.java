package com.dapid.agent.controllers.routes;

import com.dapid.agent.App;
import com.dapid.agent.context.AddJobContext;
import com.dapid.agent.context.JsonRequired;
import com.dapid.agent.models.AnnotatedDeserializer;
import com.dapid.agent.models.Job;
import com.dapid.agent.models.Jobs;
import com.dapid.agent.services.PhoneHome;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static com.github.choonchernlim.betterPreconditions.preconditions.PreconditionFactory.expect;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public class RunJobRoute implements Route, ExitRoute {
    private static final Logger log = LoggerFactory.getLogger(RunJobRoute.class);

    private ConcurrentHashMap<java.util.UUID, Jobs> map;
    private String serverHost;
    private String serverPort;
    private String instanceApi;
    private PhoneHome httpClient;
    private Gson gson;

    public RunJobRoute(ConcurrentHashMap<UUID, Jobs> map, String serverHost, String serverPort, String instanceApi, PhoneHome httpClient) {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(JsonRequired.class, new AnnotatedDeserializer<JsonRequired>())
                .create();

        this.map = map;
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.instanceApi = instanceApi;
        this.httpClient = httpClient;
    }

    private String execute(Response res, String payload) {
        expect(payload, "payload").not().toBeNull().not().toBeBlank().check();
        AddJobContext context;
        try {
            context = this.gson.fromJson(payload, AddJobContext.class);
        } catch (JsonSyntaxException e) {
            return this.exit(res, HttpStatus.BAD_REQUEST_400, "invalid json", e);
        }
        if (!context.valid()) {
            return this.exit(res, HttpStatus.BAD_REQUEST_400, "missing required fields", null);
        }

        log.info(String.format("Adding job to the queue: %s", context.jobInstanceId));
        this.map.put(
                context.jobInstanceId,
                new Jobs(
                        new Job(context),
                        this.serverHost,
                        this.serverPort,
                        this.instanceApi,
                        this.httpClient
                )
        );

        return this.exit(res, HttpStatus.OK_200, "scheduled", null);
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        return execute(response, request.body());
    }
}
