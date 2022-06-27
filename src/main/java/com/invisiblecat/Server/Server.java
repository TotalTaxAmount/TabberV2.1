package com.invisiblecat.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {

    private static final int port = 4765;
    private static ArrayList<ServerThread> clients = new ArrayList<>();
    public static ServerSocket serverSocket = null;
    public static Socket socket = null;

    private static LinkedBlockingQueue<String> log = new LinkedBlockingQueue<>();

    public static void main(String[] args) throws IOException {

        try {
            serverSocket = new ServerSocket(port);
            log.add("Server is online on port " + port);
        } catch (IOException e) {
            log.add("Error starting server socket: ");
            e.printStackTrace();
            return;
        }

        new Thread(() -> {
            while (log.iterator().hasNext()) {
                String p = log.iterator().next();
                System.out.print(p);
                log.remove(p);
            }
        }).start();

        while (true) {
//            Scanner reader = new Scanner(System.in);
//            System.out.println("Enter command");
//            System.out.print("> ");
//
//            String cmd = reader.next();


            new Thread(() -> {
                try {
                    socket = serverSocket.accept();

                    ServerThread client = new ServerThread(socket);



                    if (clients.size() == 0) {
                        client.start();
                    }
                    client.setName("Client-" + clients.size());
                    clients.add(client);
                    log.add(client.getName() + " connected");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            if (clients.size() == 0) {
                continue;
            }

            try {
                for (ServerThread client : clients) {
                    if (!client.isConnected()) {
                        log.add(client.getName() + " disconnected");
                        client.socket.close();
                        client.interrupt();
                        clients.remove(client);

                    }
                }
            } catch (ConcurrentModificationException ignored) {
                log.add("Error for looping through clients for some reason");
            }
        }
    }

}
