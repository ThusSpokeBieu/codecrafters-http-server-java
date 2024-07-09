import com.github.gmessiasc.hermes4j.core.server.HttpServer;
import java.io.IOException;

public class Main {
  public static void main(String[] args) {
     try (final HttpServer server = MyServer.getServer()) {
       server.start();
     } catch (IOException e) {
       System.out.println("IOException: " + e.getMessage());
     }
  }
}
