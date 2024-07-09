package com.github.gmessiasc.hermes4j.core.server;

import com.github.gmessiasc.hermes4j.core.endpoints.HttpEndpoint;
import com.github.gmessiasc.hermes4j.core.requests.HttpRequest;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ExecutorService;

public class HttpServer implements AutoCloseable {
  private final int port;
  private final Set<HttpEndpoint> endpoints;
  private final ExecutorService executor;

  private ServerSocket serverSocket;
  private boolean isRunning = false;

  public HttpServer(
      final int port,
      final Set<HttpEndpoint> endpoints,
      final ExecutorService executor) {
    this.port = port;
    this.endpoints = Collections.unmodifiableSet(endpoints);
    this.executor = executor;
  }

  public int getPort() {
    return port;
  }

  public boolean isRunning() {
    return isRunning;
  }

  public Set<HttpEndpoint> getEndpoints() {
    return endpoints;
  }

  public ExecutorService getExecutor() {
    return executor;
  }

  public ServerSocket getServerSocket() {
    return serverSocket;
  }

  public void handleSocket(final Socket socket) throws IOException {
    final HttpRequest request = SocketDecoder.decode(socket);
    final var endpoint = endpoints.stream()
        .filter(e -> e.path().equals(request.path()))
        .filter(e -> e.methods().contains(request.method()))
        .findFirst();

    if (endpoint.isEmpty()) {
      SocketEncoder.encode(socket, SocketEncoder.RESPONSE_NOT_FOUND);
      return;
    }

    SocketEncoder.encode(socket, SocketEncoder.RESPONSE_OK);
  }

  public void start() throws IOException {
    this.serverSocket = new ServerSocket(port);
    serverSocket.setReuseAddress(true);

    while(isRunning()) {
      final var socket = serverSocket.accept();
      executor.submit(() -> handleSocket(socket));
    }

    close();
  }

  @Override
  public void close() throws IOException {
    if (executorIsRunning()) {
      executor.shutdownNow();
      executor.close();
    }

    if (serverSocket != null) {
      serverSocket.close();
    }
  }

  private boolean executorIsRunning() {
    final boolean executorIsNotNull = executor != null;

    if (executorIsNotNull) {
      return !executor.isShutdown() || !executor.isTerminated();
    }

    return false;
  }

  public interface Builder {
    Builder port(final int port);
    Builder addEndpoints(final HttpEndpoint ...endpoints);
    Builder executor(final ExecutorService executor);
    HttpServer build() throws IOException;
  }


}
