package handlers;

import com.github.gmessiasc.hermes4j.core.handlers.HttpHandler;
import com.github.gmessiasc.hermes4j.core.headers.HttpStatus;
import com.github.gmessiasc.hermes4j.core.headers.mime.MimeTypes;
import com.github.gmessiasc.hermes4j.core.requests.HttpRequest;
import com.github.gmessiasc.hermes4j.core.responses.HttpResponse;
import com.github.gmessiasc.hermes4j.core.responses.HttpResponseBuilder;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class UserAgentHandler implements HttpHandler {

  @Override
  public HttpResponse apply(final HttpRequest request) {
    Optional<String> optionalUserAgent = Optional.empty();

    for (Map.Entry<String, Set<String>> entry : request.httpHeaders().entrySet()) {
      final String key = entry.getKey();

      if(key.equalsIgnoreCase("User-Agent")) {
        optionalUserAgent = entry.getValue().stream().findAny();
      }
    }

    if(optionalUserAgent.isEmpty()) {
      return HttpResponseBuilder
          .builder()
          .status(HttpStatus.BAD_REQUEST)
          .body("Header 'User-Agent' not found")
          .withContentType(MimeTypes.TEXT_PLAIN)
          .withContentLength()
          .build();
    }

    return HttpResponseBuilder
        .builder()
        .status(HttpStatus.OK)
        .body(optionalUserAgent.get())
        .withContentType(MimeTypes.TEXT_PLAIN)
        .withContentLength()
        .build();
  }
}
