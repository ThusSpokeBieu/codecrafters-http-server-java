package com.github.gmessiasc.hermes4j.core.server;

import com.github.gmessiasc.hermes4j.core.endpoints.HttpEndpoint;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;

public class HttpServerBuilder implements HttpServer.Builder {
  private int port;
  private Set<HttpEndpoint> endpoints = new HashSet<>();
  private ExecutorService executor;

  public static HttpServerBuilder builder() {
    return new HttpServerBuilder();
  }

  @Override
  public HttpServer.Builder port(final int port) {
    this.port = port;
    return this;
  }

  @Override
  public HttpServer.Builder addEndpoints(final HttpEndpoint... endpoints) {
    for (HttpEndpoint endpoint : endpoints) {
      this.endpoints.add(endpoint);
    }
    return this;
  }

  @Override
  public HttpServer.Builder executor(final ExecutorService executor) {
    this.executor = executor;
    return this;
  }

  @Override
  public HttpServer build() {
    return new HttpServer(this.port, this.endpoints, this.executor);
  }
}
