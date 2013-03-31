package com.garrettheel.moco;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;

/**
 * A generic Mojo defining the base requirements for using a Moco server.
 */
public abstract class AbstractMocoExecutionMojo extends AbstractMojo {

    /**
     * The file containing the JSON configuration.
     */
    @Parameter(required = true)
    protected File configFile;

    /**
     * The port to start the server on.
     */
    @Parameter(required = true)
    protected Integer port;

    /**
     * The port to stop the server on (optional).
     */
    @Parameter(required = false, defaultValue = "8082")
    protected Integer stopPort;

    public File getConfigFile() {
        return configFile;
    }

    public void setConfigFile(File configFile) {
        this.configFile = configFile;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getStopPort() {
        return stopPort;
    }

    public void setStopPort(Integer stopPort) {
        this.stopPort = stopPort;
    }

    protected void checkParams() throws MojoExecutionException {
        if (!configFile.exists()) {
            throw new MojoExecutionException("Moco config file does not exist.");
        }
        if (port == null || port < 1) {
            throw new MojoExecutionException("Invalid port number specified.");
        }
    }

}
