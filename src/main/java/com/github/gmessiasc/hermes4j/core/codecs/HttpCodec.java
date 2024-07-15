package com.github.gmessiasc.hermes4j.core.codecs;

import com.github.gmessiasc.hermes4j.core.headers.HttpHeader;
import com.github.gmessiasc.hermes4j.core.methods.HttpMethod;
import com.github.gmessiasc.hermes4j.core.paths.HttpPath;
import com.github.gmessiasc.hermes4j.core.paths.HttpPathBuilder;
import com.github.gmessiasc.hermes4j.core.requests.HttpRequest;
import com.github.gmessiasc.hermes4j.core.requests.HttpRequestBuilder;
import com.github.gmessiasc.hermes4j.core.responses.HttpResponse;
import com.github.gmessiasc.hermes4j.utils.StrUtils;
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
      final int contentLength = headers
          .getOrDefault("Content-Length", Set.of("0"))
          .stream()
          .mapToInt(Integer::valueOf)
          .sum();


      var requestBuilder = HttpRequestBuilder
          .builder()
          .path(path)
          .method(method)
          .header(headers);

      if(!method.equals(HttpMethod.GET) || contentLength == 0) {
        requestBuilder.body(readBody(reader, contentLength));
      }

      return requestBuilder.build();
    } catch (IOException exception) {
      exception.printStackTrace();
      throw new RuntimeException(exception);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  @Override
  public void encode(final Socket socket, final HttpResponse response) throws IOException {
    try {
      final var outputStream = socket.getOutputStream();
      final var httpVersion = response.httpVersion();
      final var status = response.status();

      outputStream.write(httpVersion.getVersion().getBytes());
      outputStream.write(StrUtils.SPACE);
      outputStream.write(status.getStatusMessageBytes());
      outputStream.write(StrUtils.CRLF_BYTE);

      for(final Map.Entry<String, Set<String>> entry : response.headers().entrySet()) {
        final var key = entry.getKey();
        final var values = entry.getValue();
        outputStream.write(key.getBytes());
        outputStream.write(StrUtils.COLON);
        outputStream.write(StrUtils.SPACE);

        int counter = 1;

        for(final String value : values) {
          outputStream.write(value.getBytes());
          if(counter < values.size()) {
            outputStream.write(StrUtils.COMMA);
            outputStream.write(StrUtils.SPACE);
            counter++;
          }
        }

        outputStream.write(StrUtils.CRLF_BYTE);
      }

      outputStream.write(StrUtils.CRLF_BYTE);

      if(response.body().isPresent()) {
        outputStream.write(response.body().get());
      }

    } catch(IOException ex) {
      throw new IOException(ex);
    }
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

  private Optional<byte[]> readBody(final BufferedReader reader, final int length) throws Exception {
    final StringBuilder sb = new StringBuilder();
    char[] buffer = new char[length];

    if (reader.read(buffer, 0, length) == -1) {
      throw new IOException("Body is with invalid length");
    }

    sb.append(buffer);

    if(sb.isEmpty()) return Optional.empty();

    final var body = sb.toString();
    return Optional.of(body.getBytes());
  }
}
