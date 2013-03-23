package com.garrettheel.moco;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class StopMonitor extends Thread {

    public static String MONITOR_KEY = "_MOCO_SERVER_MONITOR_KEY";

    private String key;
    private ServerSocket serverSocket;

    public StopMonitor(int port, String key) throws IOException {
        this.key = key;

        this.setDaemon(true);
        this.setName("MocoStopPluginMonitor");

        serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
        serverSocket.setReuseAddress(true);
    }

    public void run() {
        while (serverSocket != null) {
            Socket socket;
            try {
                socket = serverSocket.accept();
                socket.setSoLinger(false, 0);

                LineNumberReader reader = new LineNumberReader(
                        new InputStreamReader(socket.getInputStream()));

                String key = reader.readLine();
                if (!key.equals(this.key)) continue;

                try {
                    socket.close();
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.exit(0);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
