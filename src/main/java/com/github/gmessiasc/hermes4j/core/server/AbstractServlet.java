package com.github.gmessiasc.hermes4j.core.server;

import com.github.gmessiasc.hermes4j.core.endpoints.HttpEndpoint;
import java.io.IOException;
import java.net.Socket;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public abstract class AbstractServlet implements AutoCloseable {
  protected final int port;
  protected final Set<HttpEndpoint> endpoints;
  protected boolean isRunning = false;

  public AbstractServlet(
      final int port,
      final Set<HttpEndpoint> endpoints) {
    this.port = port;
    this.endpoints = Collections.unmodifiableSet(endpoints);
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
}
