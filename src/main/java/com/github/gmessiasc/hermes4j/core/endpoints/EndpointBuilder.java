package com.github.gmessiasc.hermes4j.core.endpoints;

import com.github.gmessiasc.hermes4j.core.codecs.Codec;
import com.github.gmessiasc.hermes4j.core.codecs.Codecs;
import com.github.gmessiasc.hermes4j.core.handlers.HttpHandler;
import com.github.gmessiasc.hermes4j.core.headers.HttpHeader;
import com.github.gmessiasc.hermes4j.core.headers.exceptions.WrongHeaderException;
import com.github.gmessiasc.hermes4j.core.headers.mime.MimeTypes;
import com.github.gmessiasc.hermes4j.core.methods.HttpMethod;
import com.github.gmessiasc.hermes4j.core.paths.PathTemplate;
import com.github.gmessiasc.hermes4j.utils.HeaderUtils;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EndpointBuilder implements HttpEndpoint.Builder {
  PathTemplate path;
  Set<HttpMethod> httpMethods = new HashSet<>();
  Map<String, Set<String>> httpHeaders = new HashMap<>();
  HttpHandler handler = null;
  Codec codec = Codecs.HTTP_CODEC;

  public static EndpointBuilder builder() {
    return new EndpointBuilder();
  }

  @Override
  public HttpEndpoint.Builder path(final String path) {
    this.path = PathTemplate.with(path);
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
  public HttpEndpoint.Builder headers(final Map<String, Set<String>> headers) {
    httpHeaders.putAll(headers);
    return this;
  }

  @Override
  public HttpEndpoint.Builder addHeader(final Map.Entry<String, Set<String>> header) {
    httpHeaders.put(header.getKey(), header.getValue());
    return this;
  }

  @Override
  public HttpEndpoint.Builder addHeader(final String keyAndValue) {
    final var header = HttpHeader.with(keyAndValue);
    if (header.isEmpty()) throw WrongHeaderException.err();

    return addHeader(header.get());
  }

  @Override
  public HttpEndpoint.Builder addHeader(final String key, final String... values) {
    final var header = HttpHeader.with(key, values);
    if (header.isEmpty()) throw WrongHeaderException.err();

    return addHeader(header.get());
  }

  @Override
  public HttpEndpoint.Builder accept(final MimeTypes... mimeTypes) {
    for (final MimeTypes mime : mimeTypes) {
      final var httpHeader = HttpHeader.with(
          HeaderUtils.ACCEPT,
          mime.getValue()
      ).get();

      addHeader(httpHeader);
    }

    return this;
  }

  @Override
  public HttpEndpoint.Builder handler(final HttpHandler aHandler) {
    this.handler = aHandler;
    return this;
  }

  @Override
  public HttpEndpoint.Builder codec(final Codec codec) {
    this.codec = codec;
    return this;
  }

  @Override
  public HttpEndpoint build() throws IOException {
    if(codec == null) throw new IOException("HttpEndpoint's codec must be initialized");
    if(handler == null) throw new IOException("HttpEndpoint's handle must be initialized");

    return new HttpEndpoint(
        path,
        Collections.unmodifiableSet(httpMethods),
        Collections.unmodifiableMap(httpHeaders),
        handler,
        codec
    );
  }
}
