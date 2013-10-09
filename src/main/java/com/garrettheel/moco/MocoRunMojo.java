package com.garrettheel.moco;

import com.github.dreamhead.moco.runner.JsonRunner;
import com.github.dreamhead.moco.runner.Runner;
import com.google.common.base.Optional;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

/**
 * Runs a Moco server with a config file and port number. Note that the server will run
 * synchronously and can be stopped by killing the process.
 */
@Mojo( name="run" )
public class MocoRunMojo extends AbstractMocoExecutionMojo {

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        checkParams();

        Runner runner;
        try {
            runner = JsonRunner.newJsonRunnerWithStreams(Arrays.asList(new FileInputStream(configFile)), Optional.of(port));
        } catch (FileNotFoundException e) {
            throw new MojoExecutionException("Unable to load config file", e);
        }
        runner.run();

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            runner.stop();
        }
    }

}
