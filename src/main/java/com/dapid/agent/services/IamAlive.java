package com.dapid.agent.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public class IamAlive {
    private static final Logger log = LoggerFactory.getLogger(IamAlive.class);

    private int healthCheckInterval;
    private String checkInUrl;
    private boolean checkedIn;

    public IamAlive(String serverProtocol, String serverHost, String serverPort, int healthCheckInterval, String clientHost) {
        this.checkedIn = false;
        this.healthCheckInterval = healthCheckInterval;
        this.checkInUrl = String.format(
                "%s://%s:%s/agent/%s",
                serverProtocol,
                serverHost,
                serverPort,
                clientHost
        );
        log.info(String.format("Starting Radio. Checkpoint: %s", this.checkInUrl));
    }

    public void say() {
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
        URL obj = new URL(checkInUrl);
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.flush();
        wr.close();

        conn.getResponseCode();

//        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//        String inputLine;
//        StringBuffer response = new StringBuffer();
//
//        while ((inputLine = in.readLine()) != null) {
//            response.append(inputLine);
//        }
//        in.close();
        if (!this.checkedIn) {
            log.info("Successfully checking in");
            this.checkedIn = true;
        }
    }
}
