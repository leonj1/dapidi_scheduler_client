package com.dapid.agent;

import com.dapid.agent.configs.Props;
import com.dapid.agent.context.AddJobContext;
import com.dapid.agent.models.Job;
import com.dapid.agent.models.JobDefinition;
import com.dapid.agent.models.Jobs;
import com.dapid.agent.services.PhoneHome;
import com.dapid.agent.services.Process;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListSet;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
@RunWith(MockitoJUnitRunner.class)
public class DispatcherTest {

    public void setUp() throws Exception {

    }

//    @Test
//    public void test() {
//        Dispatcher dispatcher = new Dispatcher(
//                new Props.Fake(),
//                new PhoneHome.Fake(),
//                new ConcurrentSkipListSet(),
//                new ConcurrentSkipListSet()
//        );
//
//        List<Jobs> jobs;
//        jobs = dispatcher.getJobs();
//
//        assertThat(jobs, not(equalTo(null)));
//    }
//
//    @Test
//    public void test2() {
//        UUID jobInstanceId = UUID.randomUUID();
//        UUID jobDefinitionId = UUID.randomUUID();
//        Dispatcher dispatcher = new Dispatcher(
//                new Props.Fake(),
//                new PhoneHome.Fake(),
//                new ConcurrentSkipListSet<Jobs>(){{
//                    add(
//                            new Jobs(
//                                    new Job(
//                                            new AddJobContext(
//                                                    jobInstanceId,
//                                                    new JobDefinition(
//                                                            jobDefinitionId,
//                                                            "fake job",
//                                                            "ls -l /tmp",
//                                                            "localhost",
//                                                            "root",
//                                                            1,
//                                                            "cron date here",
//                                                            45,
//                                                            3
//                                                    )
//                                            ),
//                                            new Process.FakeSuccessfulRun()
//                                    )
//                            )
//                    );
//                }},
//                new ConcurrentSkipListSet()
//        );
//
//        List<Jobs> jobs;
//        jobs = dispatcher.getJobs();
//
//        assertThat(jobs.size(), equalTo(1));
//    }
}
