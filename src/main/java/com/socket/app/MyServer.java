package com.socket.app;

import java.net.ServerSocket;
import java.util.HashMap;

public class MyServer {

    private ServerSocket serverSocket;
    HashMap<String, String> hmData = new HashMap<>();

    public void startServer(int port) {
        try {
            System.out.println(String.format("Server started on port %d", port));
            serverSocket = new ServerSocket(port);

            while (true) {
                System.out.println("accepting new client...");
                new ThreadClientListener(serverSocket.accept(), hmData).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(String.format("Unexpected error on server : %s", e.getMessage()));
        }

    }

    public  void stop() {
        try {
            System.out.println("stopping server...");
            serverSocket.close();
        } catch (Exception e) {

        }
    }


    public static void main(String[] args)  {
        int port = 7777;
        MyServer server = new MyServer();
        server.startServer(port);
        server.stop();
    }
}