package com.dapid.agent.services;

import com.dapid.agent.models.CheckInContext;
import com.google.gson.Gson;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public class PhoneHomeThread {
    private static final Logger log = LoggerFactory.getLogger(PhoneHomeThread.class);
    private AppHttpClient appHttpClient;
    private int healthCheckInterval;
    private String clientHostName;
    private String clientProtocol;
    private String clientPort;
    private Gson gson;

    public PhoneHomeThread(AppHttpClient appHttpClient, int healthCheckInterval, String clientHostName, String clientProtocol, String clientPort) {
        this.appHttpClient = appHttpClient;
        this.healthCheckInterval = healthCheckInterval;
        this.clientHostName = clientHostName;
        this.clientProtocol = clientProtocol;
        this.clientPort = clientPort;
        this.gson = new Gson();
    }

    public void start() {
        log.info("Kicking off checking thread in background");
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        Runnable periodicTask = new Runnable() {
            public void run() {
                try {
                    Thread.currentThread().setName("ping");
                    checkIn();
                } catch (Exception e) {
                    log.error(String.format("Problem checking in. Error %s", e.getMessage()));
                }
            }
        };

        // TODO put this into application.properties
        executor.scheduleAtFixedRate(periodicTask, 0, this.healthCheckInterval, TimeUnit.SECONDS);
    }

    private void checkIn() throws Exception {
        log.debug("Ping Checking in");
        WebRestResponse webRestResponse = this.appHttpClient.registerSelf(
                this.gson.toJson(
                        new CheckInContext(
                                this.clientHostName,
                                String.format("%s://%s:%s",
                                        this.clientProtocol,
                                        this.clientHostName,
                                        this.clientPort
                                )
                        )
                )
        );
        if (webRestResponse.status() == HttpStatus.OK_200) {
            log.info("Successfully checking in");
        }
    }
}
