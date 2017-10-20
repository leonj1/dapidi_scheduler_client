package com.dapid.agent;

import com.dapid.agent.configs.AppProperties;
import com.dapid.agent.controllers.RadioController;
import com.dapid.agent.controllers.routes.HealthCheckRoute;
import com.dapid.agent.controllers.routes.ListJobsRoute;
import com.dapid.agent.controllers.routes.AddNewJobRoute;
import com.dapid.agent.models.Jobs;
import com.dapid.agent.services.HttpPhoneHome;
import com.dapid.agent.services.StartJobs;
import com.dapid.agent.services.PhoneHomeThread;
import com.josemleon.CommandlineParser;
import com.josemleon.GetEffectiveProperty;
import com.josemleon.GetProperty;
import com.josemleon.Parser;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static spark.Spark.port;

/**
 * Hello world!
 *
 */
public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);
    private static final String APPLICATION_PROPERTIES = "application.properties";

    public static void main(String args[]) throws Exception {
        log.info("Some succeed because they are destined to. Most succeed because they are determined to. -- Unknown");

        AppProperties appProperties = null;
        Parser cmdlineParser = new CommandlineParser(args);
        try {
            appProperties = new AppProperties(
                    new GetEffectiveProperty(
                            new GetProperty(
                                    APPLICATION_PROPERTIES,
                                    cmdlineParser
                            ),
                            cmdlineParser
                    )
            );
        } catch (Exception e) {
            log.error(String.format("Really bad problem trying to find resource %s", APPLICATION_PROPERTIES));
            System.exit(1);
        }

        port(appProperties.httpServerPort());

        ConcurrentHashMap jobs = new ConcurrentHashMap<UUID, Jobs>();

        PhoneHomeThread iamAlive = new PhoneHomeThread(
                appProperties.getServerProtocol(),
                appProperties.getServerHost(),
                appProperties.getServerPort(),
                appProperties.getHealthCheckInterval(),
                appProperties.getClientHost()
        );
        iamAlive.start();

        RadioController radioController = new RadioController(
                new HealthCheckRoute(),
                new ListJobsRoute(jobs),
                new AddNewJobRoute(
                        jobs,
                        appProperties.getServerHost(),
                        appProperties.getServerPort(),
                        appProperties.getJobInstanceApi(),
                        new HttpPhoneHome(HttpClientBuilder.create().build())
                )
        );
        radioController.expose();

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(
                new StartJobs(jobs),
                0,
                1,
                TimeUnit.MINUTES
        );
    }
}
