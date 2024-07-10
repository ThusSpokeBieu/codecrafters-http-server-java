package com.github.gmessiasc.hermes4j.core.requests;

import com.github.gmessiasc.hermes4j.core.headers.HttpHeader;
import com.github.gmessiasc.hermes4j.core.headers.HttpVersion;
import com.github.gmessiasc.hermes4j.core.methods.HttpMethod;
import com.github.gmessiasc.hermes4j.core.paths.HttpPath;
import com.github.gmessiasc.hermes4j.core.paths.HttpPathBuilder;
import com.github.gmessiasc.hermes4j.core.paths.PathParams;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class HttpRequestBuilder implements HttpRequest.Builder {
  HttpVersion httpVersion = HttpVersion.HTTP_1_1;
  HttpPath httpPath = HttpPathBuilder.with("/");
  HttpMethod httpMethod = HttpMethod.GET;
  Map<String, Set<String>> httpHeader = new HashMap<>();
  PathParams pathParams = new PathParams();
  Optional<String> body = Optional.empty();

  public static HttpRequestBuilder builder() {
    return new HttpRequestBuilder();
  }

  @Override
  public HttpRequest.Builder httpVersion(final HttpVersion httpVersion) {
    this.httpVersion = httpVersion;
    return this;
  }

  @Override
  public HttpRequest.Builder path(final HttpPath path) {
    this.httpPath = path;
    return this;
  }

  @Override
  public HttpRequest.Builder method(final HttpMethod method) {
    this.httpMethod = method;
    return this;
  }

  @Override
  public HttpRequest.Builder pathParams(final PathParams pathParams) {
    this.pathParams = pathParams;
    return this;
  }

  @Override
  public HttpRequest.Builder header(final Map<String, Set<String>> header) {
    this.httpHeader = header;
    return this;
  }

  @Override
  public HttpRequest.Builder body(final Optional<String> body) {
    this.body = body;
    return this;
  }

  @Override
  public HttpRequest build() {
    return new HttpRequest(
        httpVersion,
        httpMethod,
        httpPath,
        pathParams,
        httpHeader,
        body
    );
  }
}
