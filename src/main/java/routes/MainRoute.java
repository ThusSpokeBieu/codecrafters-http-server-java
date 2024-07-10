package routes;

import com.github.gmessiasc.hermes4j.core.codecs.Codecs;
import com.github.gmessiasc.hermes4j.core.endpoints.EndpointBuilder;
import com.github.gmessiasc.hermes4j.core.endpoints.HttpEndpoint;
import com.github.gmessiasc.hermes4j.core.methods.HttpMethod;
import handlers.EchoHandler;
import handlers.MainHandler;
import handlers.UserAgentHandler;
import java.io.IOException;

public class MainRoute {
  public static HttpEndpoint mainRoute() throws IOException {
    return EndpointBuilder
        .builder()
        .path("/")
        .codec(Codecs.HTTP_CODEC)
        .methods(HttpMethod.GET)
        .handler(new MainHandler())
        .build();
  }

  public static HttpEndpoint echoRoute() throws IOException {
    return EndpointBuilder
        .builder()
        .path("/echo/{str}")
        .codec(Codecs.HTTP_CODEC)
        .methods(HttpMethod.GET)
        .handler(new EchoHandler())
        .build();
  }

  public static HttpEndpoint userAgentRoute() throws IOException {
    return EndpointBuilder
        .builder()
        .path("/user-agent")
        .codec(Codecs.HTTP_CODEC)
        .methods(HttpMethod.GET)
        .handler(new UserAgentHandler())
        .build();
  }

  public static HttpEndpoint filesRoute() throws IOException {
    return EndpointBuilder
        .builder()
        .path("/files/{filename}")
        .codec(Codecs.HTTP_CODEC)
        .methods(HttpMethod.GET)
        .handler(new UserAgentHandler())
        .build();
  }
}
