package Client;

import java.io.DataOutputStream;
import java.io.Reader;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] arg) {
        try {

            Socket socketConnection = new Socket("127.0.0.1", 4765);


            //QUERY PASSING
            DataOutputStream outToServer = new DataOutputStream(socketConnection.getOutputStream());

            Scanner reader = new Scanner(System.in);

            System.out.println("IDK: ");
            System.out.print("> ");
            String SQL = reader.next();

            outToServer.writeUTF(SQL);


        } catch (Exception e) {System.out.println(e); }
    }
}
