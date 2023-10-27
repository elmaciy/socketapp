package com.socket.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class MyServer {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    HashMap<String, String> hmData = new HashMap<>();

    public void startServer(int port) {
        try {
            System.out.println(String.format("Server socket listening port %d", port));
            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));





            while(true) {
                String command = null;

                if (in.ready()) {
                    command = in.readLine();
                }

                if (command == null) {
                    try { Thread.sleep(100); } catch (Exception e) {}
                    continue;
                }

                System.out.println(String.format("new command received from a cliend : %s" , command));

                if (command.startsWith("write ")) {
                    String data = command.substring(6).strip();
                    String[] arr = data.split("\\|");
                    String clientId = arr[0];
                    String firstName = arr[1];
                    String lastName = arr[2];

                    System.out.println(String.format("writing to database : [%s] = %s, %s", clientId, firstName, lastName));
                    hmData.put(clientId, String.format("%s|%s", firstName, lastName));
                    out.println("<SUCCESS>");
                    out.flush();

                }
                else if (command.startsWith("read ")) {
                    String clientId = command.substring(5).strip();
                    if (hmData.containsKey(clientId)) {
                        System.out.println("data found for client : " + clientId);
                        out.println(hmData.get(clientId));
                    } else {
                        System.out.println("data not found for client : " + clientId);
                        out.println("<NO_DATA_FOUND>");
                    }
                }
                else {
                    out.println("unrecognised command");
                    clientSocket.close();
                }

            }



        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(String.format("Unexpected error on server : %s", e.getMessage()));
        }

    }

    public  void stop() {
        try {
            System.out.println("stopping server...");
            in.close();
            out.close();
            clientSocket.close();
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