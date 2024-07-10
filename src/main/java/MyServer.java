import com.github.gmessiasc.hermes4j.core.server.HttpServer;
import com.github.gmessiasc.hermes4j.core.server.HttpServerBuilder;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import routes.MainRoute;

public class MyServer {
  public static final int PORT = 4221;
  public static final ExecutorService EXECUTOR = Executors.newVirtualThreadPerTaskExecutor();

  public static HttpServer getServer() throws IOException {
    return HttpServerBuilder
        .builder()
        .port(PORT)
        .executor(EXECUTOR)
        .addEndpoints(
            MainRoute.mainRoute(),
            MainRoute.echoRoute())
        .build();
  }
}
