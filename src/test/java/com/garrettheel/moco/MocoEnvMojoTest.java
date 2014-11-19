package com.garrettheel.moco;

import org.apache.http.client.fluent.Request;
import org.junit.Test;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MocoEnvMojoTest extends AbstractMocoMojoTest{
    @Test
    public void testRun() throws Exception {
        File pom = getTestFile("src/test/resources/test-env-pom.xml");
        assertTrue(pom.exists());

        final MocoRunMojo runMojo = (MocoRunMojo) lookupMojo("run", pom);
        assertNotNull(runMojo);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(getMojoExecutionTask(runMojo));

        waitForMocoStartCompleted(runMojo.getPort());

        String getResponse = Request.Get(getMocoUri(runMojo.getPort())).execute().returnContent().asString();
        assertEquals("foo", getResponse);

        executorService.shutdownNow();
        executorService.awaitTermination(1, TimeUnit.SECONDS);
    }

    public void testStart() throws Exception {
        File pom = getTestFile("src/test/resources/test-env-pom.xml");
        assertTrue(pom.exists());

        MocoStartMojo startMojo = (MocoStartMojo) lookupMojo("start", pom);
        MocoStopMojo stopMojo = (MocoStopMojo) lookupMojo("stop", pom);
        String mocoUri = getMocoUri(startMojo.getPort());

        assertNotNull(startMojo);
        assertNotNull(stopMojo);

        startMojo.execute();

        waitForMocoStartCompleted(startMojo.getPort());
        String getResponse = Request.Get(mocoUri).execute().returnContent().asString();
        assertEquals("foo", getResponse);

        stopMojo.execute();

        assertTrue(isServerShutdown(mocoUri));
    }
}
