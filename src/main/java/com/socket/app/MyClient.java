package com.socket.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.SyncFailedException;
import java.net.Socket;

public class MyClient {

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(String.format("Connection is not established : error => %s", e.getMessage()));
        }


    }

    public String sendMessage(String msg) {
        try {
            System.out.println(String.format("sending message to the server : %s", msg));
            System.out.println("#1");
            out.println(msg);
            System.out.println("#2");
            out.flush();
            System.out.println("#4");
            String resp = in.readLine();
            System.out.println("#5");
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(String.format("Unexpeced error : %s", e.getMessage()));
            return null;
        }
    }

    public void stopConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (Exception e) {

        }
    }

    private static final int MODE_READ = 0;
    private static final int MODE_WRITE = 1;

    public static void main(String[] args) {

        int mode = MODE_READ;

        String msgToSend  = null;
        if (args.length==1) {
            String cllientId = args[0];
            msgToSend = String.format("read %s",cllientId);
            mode = MODE_READ;
        }
        else if (args.length==3) {
            String cllientId = args[0];
            String firstName = args[1];
            String lastName = args[2];
            msgToSend =String.format("write %s|%s|%s", cllientId, firstName, lastName);
            mode = MODE_WRITE;
        }
        else {
            System.out.println("invalid arguments");
            System.exit(0);
        }

        MyClient client = new MyClient();
        client.startConnection("127.0.0.1", 7777);
        String response = client.sendMessage(msgToSend);
        System.out.println("from server : " + response);
        if (mode == MODE_READ) {
            if (response.equals("<NO_DATA_FOUND>")) {
                System.out.println("No data found in database");
                System.exit(0);
            }

            String[] arr = response.split("\\|");
            String firstName = arr[0];
            String lasName = arr[1];
            System.out.println(String.format("This is your data read from server database: First Name :  %s, Last Name : %s", firstName, lasName));
        } else {
            System.out.println(String.format("Your data is successfully written to server database"));
        }
        client.stopConnection();
    }


}
