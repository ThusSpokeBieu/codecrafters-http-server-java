package com.github.gmessiasc.hermes4j.core.codecs;

import com.github.gmessiasc.hermes4j.core.headers.HttpHeader;
import com.github.gmessiasc.hermes4j.core.requests.HttpRequest;
import com.github.gmessiasc.hermes4j.core.requests.HttpRequestBuilder;
import com.github.gmessiasc.hermes4j.core.responses.HttpResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Logger;

public class HttpCodec implements Codec<HttpRequest, HttpResponse> {
  private static final Logger logger = Logger.getLogger(HttpCodec.class.getName());

  @Override
  public HttpRequest decode(final Socket socket) {
    try (final var inputStream = socket.getInputStream()) {
      final var reader = new BufferedReader(new InputStreamReader(inputStream));
      String line = reader.readLine();
      logger.info("Received: " + line);

      String[] httpRequestStr = line.split(" ");
      return HttpRequestBuilder.with(httpRequestStr);
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }

  @Override
  public void encode(final Socket socket, final HttpResponse response) throws IOException {
    final var bytes = encode(response);
    final var output = socket.getOutputStream();
    output.write(bytes);
  }

  private byte[] encode(final HttpResponse response) {
    final var sb = new StringBuilder();
    final var httpVersion = response.httpVersion();
    final var status = response.status();

    sb.append(httpVersion.getVersion());
    sb.append(" ");
    sb.append(status.getStatusMessage());
    sb.append("\r\n");

    for (final HttpHeader header : response.headers()) {
      sb.append(header.key());
      sb.append(": ");

      int counter = 0;
      sb.append(header.values()[counter]);

      while (counter < header.values().length - 1) {
        counter++;
        sb.append(", ");
        sb.append(header.values()[counter]);
      }

      sb.append("\r\n");
    }

    sb.append("\r\n");

    if(response.body().isPresent()) {
      sb.append(response.body().get());
    }

    final String str = sb.toString();
    logger.info("Returned: " + str);
    return str.getBytes();
  }
}
