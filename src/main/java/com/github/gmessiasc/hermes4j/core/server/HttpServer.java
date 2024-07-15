package com.github.gmessiasc.hermes4j.core.server;

import com.github.gmessiasc.hermes4j.core.codecs.Codec;
import com.github.gmessiasc.hermes4j.core.codecs.Codecs;
import com.github.gmessiasc.hermes4j.core.endpoints.HttpEndpoint;
import com.github.gmessiasc.hermes4j.core.handlers.HttpCompressionHandler;
import com.github.gmessiasc.hermes4j.core.paths.PathTemplate;
import com.github.gmessiasc.hermes4j.core.requests.HttpRequest;
import com.github.gmessiasc.hermes4j.core.responses.HttpResponse;
import com.github.gmessiasc.hermes4j.core.responses.Responses;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class HttpServer implements AutoCloseable {
  private static final Logger logger = Logger.getLogger(HttpServer.class.getName());

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
    final HttpRequest request = Codecs.HTTP_CODEC.decode(socket);

    final var optionalEndpoint = endpoints.stream()
        .filter(e -> e.path().verify(request))
        .filter(e -> e.methods().contains(request.method()))
        .findAny();

    if (optionalEndpoint.isEmpty()) {
      Codecs.HTTP_CODEC.encode(socket, Responses.NOT_FOUND);
      return;
    }

    final var endpoint = optionalEndpoint.get();

    var endpointPath = endpoint.path();

    endpointPath.extractParam(request);

    HttpResponse response;

    if(request.isCompressed()) {
      final var decompressedRequest = HttpCompressionHandler.decompress(request);
      response = endpoint.handler().apply(decompressedRequest);
    } else {
      response = endpoint.handler().apply(request);
    }

    if(request.hasAcceptedContent()) {
      response = HttpCompressionHandler.compress(response, request);
    }

    endpoint.codec().encode(socket, response);
  }

  public void start() throws IOException {
    this.serverSocket = new ServerSocket(port);
    serverSocket.setReuseAddress(true);
    System.out.println(getMessage());

    this.isRunning = true;

    while(isRunning()) {
      final var socket = serverSocket.accept();
      socket.setReuseAddress(true);
      socket.setSoTimeout(100000);
      socket.setTcpNoDelay(true);


      executor.submit(() -> {
        try {
          handleSocket(socket);
        } catch (IOException err) {
          throw new UncheckedIOException(err);
        }
      });
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

  private String getMessage() {
    final StringBuilder sb = new StringBuilder();
    sb.append("\n\t————————————————————————————————————————————————————\n");
    sb.append("\t\tSERVER IS RUNNING!! \n");
    sb.append("\t\tTYPE: HTTP-SERVER");
    sb.append("\n\t\tLISTENING TO PORT: ");
    sb.append(port);
    sb.append("\n\n");
    sb.append("\t\tENDPOINTS: \n");

    endpoints.forEach(endpoint -> {

      sb.append("\t\t");
      sb.append(endpoint.methods().toString());
      sb.append("\t\t");

      sb.append(endpoint.path().path());
      sb.append("\n");
    });

    sb.append("\n");
    sb.append("\t————————————————————————————————————————————————————");

    return sb.toString();
  }

  public interface Builder {
    Builder port(final int port);
    Builder addEndpoints(final HttpEndpoint ...endpoints);
    Builder executor(final ExecutorService executor);
    HttpServer build() throws IOException;
  }


}
