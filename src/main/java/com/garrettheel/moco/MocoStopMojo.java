package com.garrettheel.moco;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Stops a Moco server that is already running.
 */
@Mojo(name = "stop")
public class MocoStopMojo extends AbstractMojo {

    /**
     * The port to stop the server on.
     */
    @Parameter(required = false)
    private Integer stopPort;


    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (stopPort == null || stopPort <= 0) {
            throw new MojoExecutionException("Stop port must be specified.");
        }

        try {
            Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), stopPort);
            socket.setSoLinger(false, 0);

            OutputStream outputStream = socket.getOutputStream();
            outputStream.write((StopMonitor.MONITOR_KEY + "\r\n").getBytes());

            outputStream.flush();
            socket.close();

            getLog().info("Stopped Moco server.");
        } catch (ConnectException e) {
            throw new MojoExecutionException("It doesn't look like Moco was running.", e);
        } catch (Exception e) {
            throw new MojoExecutionException("Unable to stop Moco.", e);
        }
    }
}
