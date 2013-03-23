package com.garrettheel.moco;

import com.github.dreamhead.moco.bootstrap.Main;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;

/**
 * Starts a Moco server with a config file and port number.
 */
@Mojo(name = "start")
public class MocoStartMojo extends AbstractMojo {

    /**
     * The file containing the JSON configuration.
     */
    @Parameter(required = true)
    private File configFile;

    /**
     * The port to start the server on.
     */
    @Parameter(required = true)
    private Integer port;

    /**
     * The port to stop the server on (optional).
     */
    @Parameter(required = false)
    private Integer stopPort;

    public void execute() throws MojoExecutionException, MojoFailureException {
        if (!configFile.exists()) {
            throw new MojoExecutionException("Moco config file does not exist.");
        }

        Main.main(new String[] {
                "-p", String.format("%d", port),    // port
                configFile.getAbsolutePath()        // config file
        });

        getLog().info("Started Moco server on port " + port);

        if (stopPort != null) {
            waitForStopSignal();
        } else {
            try {
                Thread.currentThread().join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void waitForStopSignal() {
        Thread monitor = null;
        try {
            monitor = new StopMonitor(stopPort, StopMonitor.MONITOR_KEY);
            monitor.start();
            monitor.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
