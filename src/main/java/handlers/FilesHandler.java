package handlers;

import com.github.gmessiasc.hermes4j.core.handlers.HttpHandler;
import com.github.gmessiasc.hermes4j.core.headers.HttpStatus;
import com.github.gmessiasc.hermes4j.core.headers.mime.MimeTypes;
import com.github.gmessiasc.hermes4j.core.methods.HttpMethod;
import com.github.gmessiasc.hermes4j.core.paths.exception.PathParamTypeException;
import com.github.gmessiasc.hermes4j.core.requests.HttpRequest;
import com.github.gmessiasc.hermes4j.core.responses.HttpResponse;
import com.github.gmessiasc.hermes4j.core.responses.HttpResponseBuilder;
import com.github.gmessiasc.hermes4j.shared.exceptions.NoStackTraceException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

public class FilesHandler implements HttpHandler {
  private static final Logger logger = Logger.getLogger(FilesHandler.class.getName());

  private static final HttpResponse NOT_FOUND = HttpResponseBuilder
      .builder()
      .status(HttpStatus.NOT_FOUND)
      .build();

  @Override
  public HttpResponse apply(final HttpRequest request) {
    try {
      return switch(request.method()) {
        case HttpMethod.GET -> getFile(request);
        case HttpMethod.POST -> postFile(request);
        default -> NOT_FOUND;
      };

    } catch (final NoStackTraceException | IOException e) {
      e.printStackTrace();
      logger.severe(e.getMessage());
      return NOT_FOUND;
    }
  }

  private static HttpResponse getFile(final HttpRequest request) throws IOException {
    final var fileName = request.pathParams().getString("filename");
    final Path path = Path.of("/tmp/data/codecrafters.io/http-server-tester/" + fileName);

    if(!Files.exists(path) || Files.isDirectory(path)) {
      return NOT_FOUND;
    }

    return HttpResponseBuilder
        .builder()
        .status(HttpStatus.OK)
        .body(Files.readString(path))
        .withContentType(MimeTypes.OCTET_STREAM)
        .withContentLength()
        .build();

  }

  private static HttpResponse postFile(final HttpRequest request) throws IOException {
    final var fileName = request.pathParams().getString("filename");
    final Path path = Path.of("./tmp/data/codecrafters.io/http-server-tester/" + fileName);

    Files.createDirectories(path.getParent());

    Files.write(path, request.body().get());

    return HttpResponseBuilder
        .builder()
        .status(HttpStatus.CREATED)
        .withContentLength()
        .build();

  }


}
