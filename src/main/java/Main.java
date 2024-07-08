import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
  public static void main(String[] args) {
    System.out.println("Logs from your program will appear here!");
    Socket clientSocket = null;

     try (var serverSocket = new ServerSocket(4221)) {
       serverSocket.setReuseAddress(true);
       clientSocket = serverSocket.accept();
       System.out.println("accepted new connection");
     } catch (IOException e) {
       System.out.println("IOException: " + e.getMessage());
     }
  }
}
