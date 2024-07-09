package com.github.gmessiasc.hermes4j.core.server;

import com.github.gmessiasc.hermes4j.core.requests.HttpRequest;
import com.github.gmessiasc.hermes4j.core.requests.HttpRequestBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public final class SocketDecoder {
  private SocketDecoder() {}

  public static HttpRequest decode(final Socket socket) {
    try (final var inputStream = socket.getInputStream()) {
      final var reader = new BufferedReader(new InputStreamReader(inputStream));
      String line = reader.readLine();
      System.out.println("Received: " + line);

      String[] httpRequestStr = line.split(" ");
      return HttpRequestBuilder.with(httpRequestStr);
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }
}
