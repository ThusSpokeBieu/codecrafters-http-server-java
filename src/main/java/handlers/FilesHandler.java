package handlers;

import com.github.gmessiasc.hermes4j.core.handlers.HttpHandler;
import com.github.gmessiasc.hermes4j.core.headers.HttpStatus;
import com.github.gmessiasc.hermes4j.core.headers.mime.MimeTypes;
import com.github.gmessiasc.hermes4j.core.paths.exception.PathParamTypeException;
import com.github.gmessiasc.hermes4j.core.requests.HttpRequest;
import com.github.gmessiasc.hermes4j.core.responses.HttpResponse;
import com.github.gmessiasc.hermes4j.core.responses.HttpResponseBuilder;
import com.github.gmessiasc.hermes4j.shared.exceptions.NoStackTraceException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FilesHandler implements HttpHandler {

  private static final HttpResponse NOT_FOUND = HttpResponseBuilder
      .builder()
      .status(HttpStatus.NOT_FOUND)
      .build();

  @Override
  public HttpResponse apply(final HttpRequest request) {
    try {
      final var fileName = request.pathParams().getString("filename");
      final Path path = Path.of("/tmp/" + fileName);

      if(!Files.exists(path) || Files.isDirectory(path)) {
        return NOT_FOUND;
      }

      return HttpResponseBuilder
          .builder()
          .status(HttpStatus.OK)
          .body(Files.readString(path))
          .withContentType(MimeTypes.TEXT_PLAIN)
          .withContentLength()
          .build();

    } catch (final NoStackTraceException | IOException e) {
      e.printStackTrace();
      return NOT_FOUND;
    }
  }
}
