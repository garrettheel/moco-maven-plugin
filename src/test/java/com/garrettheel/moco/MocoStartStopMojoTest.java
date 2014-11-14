package com.garrettheel.moco;

import com.garrettheel.moco.util.Waiter;
import org.apache.http.client.fluent.Request;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static com.garrettheel.moco.util.Waiter.Condition;

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

    private boolean isServerShutdown(final String uri) throws Exception {
        getWaiter().until(new Condition() {
            @Override
            public boolean check() {
                try {
                    Request.Get(uri).execute();
                    return false;
                } catch (IOException e) {
                    return true;
                }
            }
        }, new Waiter.TimeOutCallBack() {
            @Override
            public void execute() {
                fail();
            }
        });
        return true;
    }

}
