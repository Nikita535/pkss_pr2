import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress serverAddress = InetAddress.getLocalHost();
        int serverPort = 777;
        try (Scanner scanner = new Scanner(System.in);
             Socket socket = new Socket(serverAddress, serverPort);
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

            while (true) {
                System.out.println((String) in.readObject());
                String word = scanner.nextLine();
                out.writeObject(word);
                if (word.equals("exit")) return;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}