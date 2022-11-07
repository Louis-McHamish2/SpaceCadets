import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class ClientThread implements Runnable {

    public DataOutputStream out;
    public DataInputStream in;
    Socket socket;

    protected BlockingQueue queue = null;

    // constructor
    public ClientThread(Socket socket, BlockingQueue queue){
        this.socket = socket;
        this.queue = queue;
    }

    public void run() {
        try {

            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());

            while (true) {
                receive();
                send();
            }
        } catch(Exception e) {

        }
    }

    public void send() throws IOException, InterruptedException {
        String message = (String) queue.take();
        System.out.println("Sending Message: " + message);
        out.writeUTF(message);
    }

    public void receive() throws IOException, InterruptedException {
        String message = in.readUTF();
        System.out.println("Server received: " + message);
        queue.put(message);
    }

}

public class Server {

    // queue to hold messages to send to each client
    static BlockingQueue queue = new ArrayBlockingQueue(1024);

    public static void main(String[] args) throws InterruptedException, IOException {

        // open a ServerSocket
        ServerSocket serverSocket = new ServerSocket(6666);
        serverSocket.setReuseAddress(true);

        while (true) {

            // open a socket for each new client
            Socket newClient = serverSocket.accept();
            System.out.println("New client connected to the server!");

            // create a new thread for each new client
            ClientThread clientThread = new ClientThread(newClient, queue);
            Thread thread = new Thread(clientThread);
            thread.start();

        }
    }
}
