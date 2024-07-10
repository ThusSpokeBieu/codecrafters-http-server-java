package handlers;

import com.github.gmessiasc.hermes4j.core.handlers.HttpHandler;
import com.github.gmessiasc.hermes4j.core.headers.HttpStatus;
import com.github.gmessiasc.hermes4j.core.requests.HttpRequest;
import com.github.gmessiasc.hermes4j.core.responses.HttpResponse;
import com.github.gmessiasc.hermes4j.core.responses.HttpResponseBuilder;

public class MainHandler implements HttpHandler {

  private static final HttpResponse response = HttpResponseBuilder
      .builder()
      .status(HttpStatus.OK)
      .build();

  @Override
  public HttpResponse apply(final HttpRequest httpRequest) {
    return response;
  }

}
