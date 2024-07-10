package handlers;

import com.github.gmessiasc.hermes4j.core.handlers.HttpHandler;
import com.github.gmessiasc.hermes4j.core.headers.HttpHeader;
import com.github.gmessiasc.hermes4j.core.headers.HttpStatus;
import com.github.gmessiasc.hermes4j.core.requests.HttpRequest;
import com.github.gmessiasc.hermes4j.core.responses.HttpResponse;
import com.github.gmessiasc.hermes4j.core.responses.HttpResponseBuilder;

public class MainHandler implements HttpHandler {

  @Override
  public HttpResponse apply(final HttpRequest httpRequest) {
    final var header = HttpHeader.with("Connection: close").get();

    var response = HttpResponseBuilder
        .builder()
        .status(HttpStatus.OK)
        .addHeader(header)
        .withContentLength()
        .build();

    return response;
  }

}
