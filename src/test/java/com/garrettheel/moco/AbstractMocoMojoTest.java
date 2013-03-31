package com.garrettheel.moco;

import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;

public abstract class AbstractMocoMojoTest extends AbstractMojoTestCase {

    private final static String MOCO_URI = "http://localhost:%d";

    protected Runnable getMojoExecutionTask(final Mojo mojo) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    mojo.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    protected String getMocoUri(int port) {
        return String.format(MOCO_URI, port);
    }

}
