package com.dapid.agent.services;

import com.dapid.agent.models.Jobs;
import com.dapid.agent.models.StartJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public class StartJobs implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(StartJobs.class);

    private ConcurrentHashMap<UUID, Jobs> map;

    public StartJobs(ConcurrentHashMap<UUID, Jobs> map) {
        this.map = map;
    }


    @Override
    public void run() {
        Thread.currentThread().setName("StartJobs");
        log.info("Starting StartJobs thread");
        for (UUID id : this.map.keySet()) {
            StartJob startJob = new StartJob(
                    id,
                    this.map.get(id),
                    map
            );
            Thread t1 = new Thread(startJob, id.toString());
            t1.start();
        }
        log.info("Ending StartJobs thread");
    }
}
