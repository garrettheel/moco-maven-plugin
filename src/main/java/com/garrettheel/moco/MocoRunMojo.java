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
 * Runs a Moco server with a config file and port number. Note that the server will run
 * synchronously and can be stopped by killing the process.
 */
@Mojo( name="run" )
public class MocoRunMojo extends AbstractMojo {

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

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        checkParams();

        Runner runner = new DynamicRunner(configFile.getAbsolutePath(), port);
        runner.run();

        getLog().info("Started Moco server on port " + port);

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void checkParams() throws MojoExecutionException {
        if (!configFile.exists()) {
            throw new MojoExecutionException("Moco config file does not exist.");
        }
        if (port == null || port < 1) {
            throw new MojoExecutionException("Invalid port number specified.");
        }
    }
}
