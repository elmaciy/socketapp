package com.socket.app;

import java.io.*;
import java.net.Socket;
import java.nio.file.Path;
import java.util.HashMap;

public class ThreadClientListener extends Thread {


    private Socket socket;

    HashMap<String, String> hmData;

    private PrintWriter out;
    private BufferedReader in;

    public ThreadClientListener(Socket clientSocket, HashMap<String, String> hmData) {
        this.socket = clientSocket;
        this.hmData = hmData;
    }

    private  void  resources() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            return;
        }
    }

    public void run() {

        resources();

        System.out.println("new client connected.");
        try {
            String command = null;


            while (true) {
                System.out.println("reading line : ");
                command = in.readLine();
                System.out.println("read line : %s" + command);
                if (command!=null) break;
            }


            System.out.println(String.format("new command received from a cliend : %s" , command));

            if (command ==null) {

            }

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
            }

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
