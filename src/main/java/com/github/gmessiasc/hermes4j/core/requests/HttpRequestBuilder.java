package com.github.gmessiasc.hermes4j.core.requests;

import com.github.gmessiasc.hermes4j.core.methods.HttpMethod;
import com.github.gmessiasc.hermes4j.core.paths.HttpPath;
import com.github.gmessiasc.hermes4j.core.paths.HttpPathBuilder;

public class HttpRequestBuilder implements HttpRequest.Builder {

  @Override
  public HttpRequest.Builder method(final HttpMethod method) {
    return null;
  }

  @Override
  public HttpRequest.Builder path(final HttpPath path) {
    return null;
  }

  @Override
  public HttpRequest.Builder httpVersion(final String httpVersion) {
    return null;
  }

  @Override
  public HttpRequest build() {
    return null;
  }

  public static HttpRequest with(final String[] splittedHttpRequest) {
    final var method = HttpMethod.get(splittedHttpRequest[0]);
    final var path = HttpPathBuilder.with(splittedHttpRequest[1]);
    final var httpVersion = splittedHttpRequest[2];

    return new HttpRequest(method, path, httpVersion);
  }
}
