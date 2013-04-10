package com.garrettheel.moco;

import com.github.dreamhead.moco.runner.DynamicRunner;
import com.github.dreamhead.moco.runner.Runner;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * Runs a Moco server with a config file and port number. Note that the server will run
 * synchronously and can be stopped by killing the process.
 */
@Mojo( name="run" )
public class MocoRunMojo extends AbstractMocoExecutionMojo {

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        checkParams();

        Runner runner = new DynamicRunner(configFile.getAbsolutePath(), port);
        runner.run();

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            runner.stop();
        }
    }

}
