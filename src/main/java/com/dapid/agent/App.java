package com.dapid.agent;

import com.dapid.agent.configs.AppProperties;
import com.dapid.agent.controllers.RadioController;
import com.dapid.agent.controllers.routes.HealthCheckRoute;
import com.dapid.agent.controllers.routes.ListJobsRoute;
import com.dapid.agent.controllers.routes.RunJobRoute;
import com.dapid.agent.models.Jobs;
import com.dapid.agent.services.FindResourceFile;
import com.dapid.agent.services.GetProperty;
import com.dapid.agent.services.HttpPhoneHome;
import com.dapid.agent.services.StartJobs;
import com.dapid.agent.services.IamAlive;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static spark.Spark.port;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);
    private static final String APPLICATION_PROPERTIES = "application.properties";

    public static void main(String args[]) {
        // TODO Make this configureable
        port(3232);
        log.info("Some succeed because they are destined to. Most succeed because they are determined to. -- Unknown");

        // Starting here, we are going the job Spring would normally do
        AppProperties appProperties = null;
        try {
            appProperties = new AppProperties(
                    new GetProperty(
                            new FindResourceFile(APPLICATION_PROPERTIES)
                    )
            );
        } catch (Exception e) {
            log.error(String.format("Really bad problem trying to find resource %s", APPLICATION_PROPERTIES));
            System.exit(1);
        }


        ConcurrentHashMap jobs = new ConcurrentHashMap<java.util.UUID, Jobs>();

        IamAlive iamAlive = new IamAlive(
                appProperties.getServerProtocol(),
                appProperties.getServerHost(),
                appProperties.getServerPort(),
                appProperties.getHealthCheckInterval(),
                appProperties.getClientHost()
        );
        iamAlive.say();

        RadioController radioController = new RadioController(
                new HealthCheckRoute(),
                new ListJobsRoute(jobs),
                new RunJobRoute(
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
