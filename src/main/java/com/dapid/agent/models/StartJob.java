package com.dapid.agent.models;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static com.github.choonchernlim.betterPreconditions.preconditions.PreconditionFactory.expect;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public class StartJob implements Runnable {

    private UUID id;
    private Jobs jobs;
    private ConcurrentHashMap<UUID, Jobs> map;

    public StartJob(UUID id, Jobs jobs, ConcurrentHashMap<UUID, Jobs> map) {
        expect(jobs, "jobs").not().toBeNull().check();
        this.id = id;
        this.jobs = jobs;
        this.map = map;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(String.format("StartJob-%s", this.id));
        Jobs jobs = this.jobs.run();
        if (jobs != null) {
            this.map.put(id, jobs);
        } else {
            this.map.remove(id);
        }
    }
}
