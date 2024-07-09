package com.github.gmessiasc.hermes4j.core.endpoints;

import com.github.gmessiasc.hermes4j.core.handlers.HttpHandler;
import com.github.gmessiasc.hermes4j.core.headers.HttpHeader;
import com.github.gmessiasc.hermes4j.core.headers.mime.MimeTypes;
import com.github.gmessiasc.hermes4j.core.methods.HttpMethod;
import com.github.gmessiasc.hermes4j.core.paths.HttpPath;
import com.github.gmessiasc.hermes4j.core.paths.HttpPathBuilder;
import com.github.gmessiasc.hermes4j.utils.HeaderUtils;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class EndpointBuilder implements HttpEndpoint.Builder {

  HttpPath path;
  Set<HttpMethod> httpMethods = new HashSet<>();
  List<HttpHeader> httpHeaders = new ArrayList<>();
  Type bodyType = null;
  HttpHandler handler = null;

  public static EndpointBuilder builder() {
    return new EndpointBuilder();
  }

  @Override
  public HttpEndpoint.Builder path(final String path) {
    this.path = HttpPathBuilder.with(path);
    return this;
  }

  @Override
  public HttpEndpoint.Builder methods(final HttpMethod... methods) {
    this.httpMethods.addAll(List.of(methods));
    return this;
  }

  @Override
  public HttpEndpoint.Builder methods(final String... methods) {
    Arrays.stream(methods)
        .map(HttpMethod::get)
        .forEach(this::methods);

    return this;
  }

  @Override
  public HttpEndpoint.Builder headers(final HttpHeader... headers) {
    this.httpHeaders.addAll(List.of(headers));
    return this;
  }

  @Override
  public HttpEndpoint.Builder addHeader(final HttpHeader header) {
    this.httpHeaders.add(header);
    return this;
  }

  @Override
  public HttpEndpoint.Builder addHeader(final String key, final String... values) {
    final var httpHeader = HttpHeader.with(key, values);
    return addHeader(httpHeader);
  }

  @Override
  public HttpEndpoint.Builder accept(final MimeTypes... mimeTypes) {
    for (final MimeTypes mime : mimeTypes) {
      final var httpHeader = HttpHeader.with(
          HeaderUtils.ACCEPT,
          mime.getValue()
      );

      addHeader(httpHeader);
    }

    return this;
  }

  @Override
  public HttpEndpoint.Builder body(final Type bodyType) {
    this.bodyType = bodyType;
    return this;
  }

  @Override
  public HttpEndpoint.Builder handler(final HttpHandler aHandler) {
    this.handler = aHandler;
    return this;
  }

  @Override
  public HttpEndpoint build() {
    return new HttpEndpoint(
        path,
        Collections.unmodifiableSet(httpMethods),
        Collections.unmodifiableList(httpHeaders),
        Optional.ofNullable(bodyType),
        handler
    );
  }
}
