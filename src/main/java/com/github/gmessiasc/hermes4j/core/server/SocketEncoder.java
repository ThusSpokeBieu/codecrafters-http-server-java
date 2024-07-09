package com.github.gmessiasc.hermes4j.core.server;

import com.github.gmessiasc.hermes4j.core.headers.HttpHeader;
import com.github.gmessiasc.hermes4j.core.headers.HttpStatus;
import com.github.gmessiasc.hermes4j.core.headers.mime.MimeTypes;
import com.github.gmessiasc.hermes4j.core.responses.HttpResponse;
import com.github.gmessiasc.hermes4j.core.responses.HttpResponseBuilder;
import java.io.IOException;
import java.net.Socket;


public final class SocketEncoder {
  private SocketEncoder() {
  }

  public static HttpResponse RESPONSE_OK = HttpResponseBuilder
      .builder()
      .status(HttpStatus.OK)
      .body("abc")
      .withContentType(MimeTypes.TEXT_PLAIN)
      .withContentLength()
      .build();

  public static HttpResponse RESPONSE_NOT_FOUND = HttpResponseBuilder
      .builder()
      .status(HttpStatus.NOT_FOUND)
      .build();

  public static void encode(final Socket socket, final HttpResponse response) throws IOException {
    final var bytes = encode(response);
    final var output = socket.getOutputStream();
    output.write(bytes);
  }

  private static byte[] encode(final HttpResponse response) {
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

    sb.append(response.body());

    final String str = sb.toString();
    System.out.println(str);
    return str.getBytes();
  }
}
