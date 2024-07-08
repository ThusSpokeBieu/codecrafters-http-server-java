import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.http.HttpRequest;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class EndpointHandler {
  public static void getPath(final Socket socket) {
    try(final var inputStream = socket.getInputStream()) {
      final var reader = new BufferedReader(new InputStreamReader(inputStream));
      String line = reader.readLine();

      System.out.println("Received: " + line);

      String[] httpRequest = line.split(" ");

      final AtomicInteger counter = new AtomicInteger(0);
      Arrays.stream(httpRequest).iterator().forEachRemaining(
          s -> System.out.println(counter.get() + " -> " + httpRequest[counter.getAndIncrement()])
      );
      final var output = socket.getOutputStream();

      switch(httpRequest[1]) {
       case "/" -> output.write(HttpUtils.OK_200_MESSAGE_BYTES);
       default -> output.write(HttpUtils.NOT_FOUND_404_MESSAGE_BYTES);
      }

    } catch (IOException e) {
      System.out.println("IOException: " + e.getMessage());
    }
  }


}
