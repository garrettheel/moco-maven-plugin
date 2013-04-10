package com.garrettheel.moco;

import com.github.dreamhead.moco.runner.DynamicRunner;
import com.github.dreamhead.moco.runner.SocketShutdownMonitorRunner;
import com.github.dreamhead.moco.runner.Runner;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * Starts a Moco server with a config file and port number. Note that the server will
 * be run asynchronously and can be stopped using the `stop` goal.
 */
@Mojo(name = "start")
public class MocoStartMojo extends AbstractMocoExecutionMojo {

    public void execute() throws MojoExecutionException, MojoFailureException {
        checkParams();

        Runner dynamicRunner = new DynamicRunner(configFile.getAbsolutePath(), port);
        Runner runner = new SocketShutdownMonitorRunner(dynamicRunner, stopPort, MONITOR_KEY);

        runner.run();
    }

    @Override
    protected void checkParams() throws MojoExecutionException {
        super.checkParams();

        if (stopPort == null || stopPort < 1) {
            throw new MojoExecutionException("Invalid stop port number specified.");
        }
    }

}
