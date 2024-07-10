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
      final var fileName = request.pathParams().getString("filename");
      final Path path = Path.of("/tmp/" + fileName);

      logger.info("FILE-NAME: " + fileName);
      logger.info("PATH: " + path);

      if(!Files.exists(path) || Files.isDirectory(path)) {
        logger.info("NÃO EXISTE OU É DIRETORIO");
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
      logger.severe(e.getMessage());
      return NOT_FOUND;
    }
  }
}
