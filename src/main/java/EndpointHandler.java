import java.io.IOException;
import java.net.Socket;

public class EndpointHandler {
  public static void getPath(final Socket socket) {
    try {
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
