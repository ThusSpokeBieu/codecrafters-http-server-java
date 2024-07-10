package routes;

import com.github.gmessiasc.hermes4j.core.codecs.Codecs;
import com.github.gmessiasc.hermes4j.core.endpoints.EndpointBuilder;
import com.github.gmessiasc.hermes4j.core.endpoints.HttpEndpoint;
import com.github.gmessiasc.hermes4j.core.headers.mime.MimeTypes;
import com.github.gmessiasc.hermes4j.utils.StrUtils;
import handlers.EchoHandler;
import handlers.MainHandler;
import handlers.UserAgentHandler;
import java.io.IOException;

public class MainRoute {
  public static HttpEndpoint mainRoute() throws IOException {
    return EndpointBuilder
        .builder()
        .path(StrUtils.SLASH)
        .accept(MimeTypes.ALL)
        .codec(Codecs.HTTP_CODEC)
        .methods("GET")
        .handler(new MainHandler())
        .build();
  }

  public static HttpEndpoint echoRoute() throws IOException {
    return EndpointBuilder
        .builder()
        .path("/echo/{str}")
        .codec(Codecs.HTTP_CODEC)
        .methods("GET")
        .handler(new EchoHandler())
        .build();
  }

  public static HttpEndpoint userAgentRoute() throws IOException {
    return EndpointBuilder
        .builder()
        .path("/user-agent")
        .codec(Codecs.HTTP_CODEC)
        .methods("GET")
        .handler(new UserAgentHandler())
        .build();
  }
}
