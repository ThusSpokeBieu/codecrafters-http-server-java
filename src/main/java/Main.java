import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
  public static void main(String[] args) {
     try (final ServerSocket serverSocket = new ServerSocket(4221)) {
       serverSocket.setReuseAddress(true);
       final Socket socket = serverSocket.accept();
       final var input = socket.getInputStream().readAllBytes();
       final var str = new String(input);

       System.out.println(str);

       System.out.println("accepted new connection");
       socket.getOutputStream().write(HttpUtils.OUTPUT_BYTE);
     } catch (IOException e) {
       System.out.println("IOException: " + e.getMessage());
     }
  }
}
