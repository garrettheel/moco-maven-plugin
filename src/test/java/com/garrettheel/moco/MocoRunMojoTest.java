package com.garrettheel.moco;

import org.apache.http.client.fluent.Request;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MocoRunMojoTest extends AbstractMocoMojoTest {

    @Before
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void testRun() throws Exception {
        File pom = getTestFile("src/test/resources/test-pom.xml");
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

}
