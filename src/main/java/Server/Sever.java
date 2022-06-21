package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Sever {

    private static final int port = 4765;
    private static ArrayList<ServerThread> clients = new ArrayList<>();

    public static ServerSocket serverSocket = null;

    public static Socket socket = null;

    public static void main(String args[]) {


        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Error starting server socket: ");
            e.printStackTrace();
            return;
        }

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

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        }
    }

}
