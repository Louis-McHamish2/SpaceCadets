import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static int port = 6666;

    static DataOutputStream out;
    static DataInputStream in;

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("localhost", port);

        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());

        Scanner scanner = new Scanner(System.in);

        while (true) {

            send(scanner.nextLine());
            receive();
        }
    }

    public static void send(String message) throws IOException {
        System.out.println("Sending Message: " + message);
        out.writeUTF(message);
    }

    public static void receive() throws IOException {
        System.out.println("Message Received: " + in.readUTF());
    }

}
