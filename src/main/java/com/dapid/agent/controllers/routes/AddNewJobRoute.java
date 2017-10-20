package com.dapid.agent.controllers.routes;

import com.dapid.agent.context.AddJobContext;
import com.dapid.agent.context.JsonRequired;
import com.dapid.agent.models.AnnotatedDeserializer;
import com.dapid.agent.models.Job;
import com.dapid.agent.models.Jobs;
import com.dapid.agent.services.PhoneHome;
import com.dapid.agent.services.SimpleExitRoute;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
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
public class AddNewJobRoute implements Route {
    private static final Logger log = LoggerFactory.getLogger(AddNewJobRoute.class);

    private ConcurrentHashMap<java.util.UUID, Jobs> map;
    private String serverHost;
    private String serverPort;
    private String instanceApi;
    private PhoneHome httpClient;
    private Gson gson;

    public AddNewJobRoute(ConcurrentHashMap<UUID, Jobs> map, String serverHost, String serverPort, String instanceApi, PhoneHome httpClient) {
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
            return SimpleExitRoute.builder(res).BAD_REQUEST_400().text("invalid json", e);
        }
        if (!context.valid()) {
            return SimpleExitRoute.builder(res).BAD_REQUEST_400().text("missing required fields");
        }

        log.info(String.format("Adding job to the queue: %s", context.getJobInstanceId()));
        this.map.put(
                context.getJobInstanceId(),
                new Jobs(
                        new Job(context),
                        this.serverHost,
                        this.serverPort,
                        this.instanceApi,
                        this.httpClient
                )
        );
        return SimpleExitRoute.builder(res).CREATED_201().text("created");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        return execute(response, request.body());
    }
}
