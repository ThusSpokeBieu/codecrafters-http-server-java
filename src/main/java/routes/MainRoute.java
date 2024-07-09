package routes;

import com.github.gmessiasc.hermes4j.core.endpoints.EndpointBuilder;
import com.github.gmessiasc.hermes4j.core.endpoints.HttpEndpoint;
import com.github.gmessiasc.hermes4j.core.headers.mime.MimeTypes;
import com.github.gmessiasc.hermes4j.utils.StrUtils;
import handlers.MainHandler;

public class MainRoute {
  public static HttpEndpoint mainRoute() {
    return EndpointBuilder
        .builder()
        .path(StrUtils.SLASH)
        .accept(MimeTypes.ALL)
        .methods("GET")
        .handler(MainHandler.get())
        .build();
  }
}
