package com.garrettheel.moco;

import com.github.dreamhead.moco.runner.DynamicRunner;
import com.github.dreamhead.moco.runner.Runner;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;

/**
 * Starts a Moco server with a config file and port number. Note that the server will
 * be run asynchronously and can be stopped using the `stop` goal.
 */
@Mojo(name = "start")
public class MocoStartMojo extends AbstractMocoExecutionMojo {

    public void execute() throws MojoExecutionException, MojoFailureException {
        checkParams();

        Runner runner = new DynamicRunner(configFile.getAbsolutePath(), port);
        runner.run();

        getLog().info("Started Moco server on port " + port);

        waitForStopSignal(runner);

    }

    protected void checkParams() throws MojoExecutionException {
        super.checkParams();

        if (stopPort == null || stopPort < 1) {
            throw new MojoExecutionException("Invalid stop port number specified.");
        }
    }

    private void waitForStopSignal(Runner runner) {
        try {
            Thread monitor = new StopMonitor(runner, stopPort, StopMonitor.MONITOR_KEY);
            monitor.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
