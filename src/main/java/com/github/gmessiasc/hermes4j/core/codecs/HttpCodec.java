package com.github.gmessiasc.hermes4j.core.codecs;

import com.github.gmessiasc.hermes4j.core.headers.HttpHeader;
import com.github.gmessiasc.hermes4j.core.methods.HttpMethod;
import com.github.gmessiasc.hermes4j.core.paths.HttpPath;
import com.github.gmessiasc.hermes4j.core.paths.HttpPathBuilder;
import com.github.gmessiasc.hermes4j.core.requests.HttpRequest;
import com.github.gmessiasc.hermes4j.core.requests.HttpRequestBuilder;
import com.github.gmessiasc.hermes4j.core.responses.HttpResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

public class HttpCodec implements Codec<HttpRequest, HttpResponse> {
  private static final Logger logger = Logger.getLogger(HttpCodec.class.getName());

  @Override
  public HttpRequest decode(final Socket socket) {
    try {
      final var inputStream = socket.getInputStream();
      final var reader = new BufferedReader(new InputStreamReader(inputStream));

      final String[] firstLine = readFirstLine(reader);
      final HttpMethod method = HttpMethod.get(firstLine[0]);

      final HttpPath path = HttpPathBuilder.with(firstLine[1]);

      final var headers = readHeader(reader);

      final Optional<String> body;

      if(method.equals(HttpMethod.GET)) {
        body = Optional.empty();
      } else {
        body = readBody(reader);
      }

      var request = HttpRequestBuilder
          .builder()
          .path(path)
          .method(method)
          .header(headers)
          .body(body)
          .build();

      logger.info("Received: " + request);

      return request;

    } catch (IOException exception) {
      throw new RuntimeException(exception);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  @Override
  public void encode(final Socket socket, final HttpResponse response) throws IOException {
    try {
      final var bytes = encode(response);
      final var output = socket.getOutputStream();
      output.write(bytes);
    } catch(IOException ex) {
      throw ex;
    }
  }

  private byte[] encode(final HttpResponse response) {
    final var sb = new StringBuilder();
    final var httpVersion = response.httpVersion();
    final var status = response.status();

    sb.append(httpVersion.getVersion());
    sb.append(" ");
    sb.append(status.getStatusMessage());
    sb.append("\r\n");

    response.headers().forEach(
        (key, values) -> {
          sb.append(key);
          sb.append(": ");

          int counter = 1;
          for (final String value : values) {
            sb.append(value);
            if (counter < values.size()) {
              sb.append(", ");
              counter++;
            }
        }
        sb.append("\r\n");
    });

    sb.append("\r\n");

    if(response.body().isPresent()) {
      sb.append(response.body().get());
    }

    final String str = sb.toString();
    logger.info("Returned: " + str);
    return str.getBytes();
  }

  private String[] readFirstLine(final BufferedReader reader) throws IOException {
    final String firstLine = reader.readLine();
    return firstLine.split(" ");
  }

  private Map<String, Set<String>> readHeader(final BufferedReader reader) throws Exception {
    final Map<String, Set<String>> headers = new HashMap<>();

    String line = reader.readLine();

    while(line != null) {
      if(line.isEmpty()) break;
      final var optionalHeader = HttpHeader.with(line);

      if(optionalHeader.isEmpty()) {
        line = reader.readLine();
        continue;
      }

      final var header = optionalHeader.get();

      headers.merge(
          header.getKey(),
          header.getValue(),
          (currentSet, newSet) -> {
            currentSet.addAll(newSet);
            return currentSet;
          });

      line = reader.readLine();
    }

    return headers;
  }

  private Optional<String> readBody(final BufferedReader reader) throws Exception {
    final StringBuilder sb = new StringBuilder();

    int intValueOfChar;

    while ((intValueOfChar = reader.read()) != -1) {
      sb.append((char) intValueOfChar);
    }

    if (sb.isEmpty()) {
      return Optional.empty();
    }

    final var body = sb.toString();

    logger.info(" \n \n \n BODY ===== " + body + "\n \n \n ");
    return Optional.of(body);
  }
}
