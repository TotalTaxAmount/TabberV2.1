package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class Server {

    private static final int port = 4765;
    private static ArrayList<ServerThread> clients = new ArrayList<>();
    public static ServerSocket serverSocket = null;
    public static Socket socket = null;

    public static void main(String args[]) throws IOException {
        
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

            if (clients.size() == 0) {
                continue;
            }

            System.out.println(clients);

            try {
                for (ServerThread client : clients) {
                    if (!client.isConnected()) {
                        System.out.println(client.getName() + " disconnected");
                        client.interrupt();
                        clients.remove(client);
                    } else {
                        System.out.println(client.getName() + " connected");
                    }
                }
            } catch (ConcurrentModificationException ignored) {
                System.out.println("Error for looping through clients for some reason");
            }
        }
    }

}
