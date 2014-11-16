package com.garrettheel.moco;

import org.apache.http.client.fluent.Request;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class MocoStartStopMojoTest extends AbstractMocoMojoTest {

    @Before
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void testStartStop() throws Exception {
        File pom = getTestFile("src/test/resources/test-pom.xml");
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

    @Test
    public void testRandomShutdownPort() throws Exception {
        File pom = getTestFile("src/test/resources/test-pom-noshutdownport.xml");
        assertTrue(pom.exists());

        MocoStartMojo startMojo = (MocoStartMojo) lookupMojo("start", pom);
        MocoStopMojo stopMojo = (MocoStopMojo) lookupMojo("stop", pom);
        String mocoUri = getMocoUri(startMojo.getPort());

        startMojo.execute();

        String shutdownPort = System.getProperty(AbstractMocoExecutionMojo.SHUTDOWN_PORT_PROPERTY_NAME);
        assertNotNull(shutdownPort);
        assertTrue(Integer.valueOf(shutdownPort) > 0);

        stopMojo.execute();

        assertTrue(isServerShutdown(mocoUri));
    }

}
