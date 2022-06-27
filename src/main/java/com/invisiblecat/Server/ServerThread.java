package com.invisiblecat.Server;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {
    protected Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inp;
        BufferedReader brinp;
        DataOutputStream out;
        try {
            inp = socket.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Error creating thread for: " + socket.getInetAddress());
            return;
        }

        String line;
        while (isConnected()) {
            try {
                line = brinp.readLine();
                if ((line == null) || line.equalsIgnoreCase("QUIT")) {
                    socket.close();
                    return;
                } else {
                    out.writeBytes(line + "\n\r");
                    out.flush();
                }
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }

        }
    }


    public boolean isConnected() {
        try {
            socket.getOutputStream().write(0);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
