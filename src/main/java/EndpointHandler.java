import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class EndpointHandler {
  public static void getPath(final Socket socket) {
    try(final var inputStream = socket.getInputStream()) {
      final var reader = new BufferedReader(new InputStreamReader(inputStream));
      String line = reader.readLine();

      System.out.println("Received: " + line);

      String[] httpRequestStr = line.split(" ");
      final HttpRequest request = HttpRequest.with(httpRequestStr);

      System.out.println(request);

      final var output = socket.getOutputStream();

      switch(request.getPath()) {
       case "/" -> output.write(HttpUtils.OK_200_MESSAGE_BYTES);
       default -> output.write(HttpUtils.NOT_FOUND_404_MESSAGE_BYTES);
      }

    } catch (IOException e) {
      System.out.println("IOException: " + e.getMessage());
    }
  }


}
