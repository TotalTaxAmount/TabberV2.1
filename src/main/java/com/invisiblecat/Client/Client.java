package com.invisiblecat.Client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static Socket socket = null;

    public static void main(String[] arg) throws InterruptedException {
        while (true) {
            try {
                socket = new Socket("127.0.0.1", 4765);
                break;
            } catch (IOException ignored) {
                System.out.println("Error connecting to Server retrying in 5 seconds...");
                Thread.sleep(5000);
            }
        }
        while (true) {
            System.out.println("Running...");
            Scanner reader = new Scanner(System.in);
            System.out.println("End? (y/n)");
            System.out.print("> ");

            String cmd = reader.next();

            if (cmd.equalsIgnoreCase("y")) {
                break;
            }

        }

    }
}

