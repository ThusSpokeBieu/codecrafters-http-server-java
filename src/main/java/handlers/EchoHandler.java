package handlers;

import com.github.gmessiasc.hermes4j.core.handlers.HttpHandler;
import com.github.gmessiasc.hermes4j.core.headers.HttpStatus;
import com.github.gmessiasc.hermes4j.core.headers.mime.MimeTypes;
import com.github.gmessiasc.hermes4j.core.requests.HttpRequest;
import com.github.gmessiasc.hermes4j.core.responses.HttpResponse;
import com.github.gmessiasc.hermes4j.core.responses.HttpResponseBuilder;

public class EchoHandler implements HttpHandler {

  @Override
  public HttpResponse apply(final HttpRequest request) {
    final String echo = request.pathParams().getString("echo");

    return HttpResponseBuilder
        .builder()
        .status(HttpStatus.OK)
        .body(echo)
        .withContentType(MimeTypes.TEXT_PLAIN)
        .withContentLength()
        .build();
  }
}
