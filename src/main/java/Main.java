import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
  public static void main(String[] args) {
     try (final ServerSocket serverSocket = new ServerSocket(4221)) {
       serverSocket.setReuseAddress(true);
       final Socket socket = serverSocket.accept();
       EndpointHandler.getPath(socket);
     } catch (IOException e) {
       System.out.println("IOException: " + e.getMessage());
     }
  }
}
