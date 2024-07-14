package com.github.gmessiasc.hermes4j.core.responses;

import com.github.gmessiasc.hermes4j.core.headers.HttpHeader;
import com.github.gmessiasc.hermes4j.core.headers.HttpStatus;
import com.github.gmessiasc.hermes4j.core.headers.HttpVersion;
import com.github.gmessiasc.hermes4j.core.headers.exceptions.WrongHeaderException;
import com.github.gmessiasc.hermes4j.core.headers.mime.MimeTypes;
import com.github.gmessiasc.hermes4j.utils.StrUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

public class HttpResponseBuilder implements HttpResponse.Builder {

  HttpVersion httpVersion = HttpVersion.HTTP_1_1;
  HttpStatus httpStatus = HttpStatus.OK;
  Map<String, Set<String>> httpHeaders = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
  Optional<String> body = Optional.empty();
  Optional<byte[]> bodyByte = Optional.empty();


  public static HttpResponseBuilder builder() {
    return new HttpResponseBuilder();
  }

  @Override
  public HttpResponseBuilder version(final HttpVersion version) {
    this.httpVersion = version;
    return this;
  }

  @Override
  public HttpResponseBuilder status(final HttpStatus status) {
    httpStatus = status;
    return this;
  }

  @Override
  public HttpResponse.Builder headers(final Map<String, Set<String>> headers) {
    httpHeaders.putAll(headers);
    return this;
  }

  @Override
  public HttpResponse.Builder addHeader(final Map.Entry<String, Set<String>> header) {
    httpHeaders.put(header.getKey(), header.getValue());
    return this;
  }

  @Override
  public HttpResponse.Builder addHeader(final String keyAndValue) {
    final var header = HttpHeader.with(keyAndValue);

    if (header.isEmpty()) throw WrongHeaderException.err();

    return addHeader(header.get());
  }

  @Override
  public HttpResponse.Builder addHeader(final String key, final String... values) {
    final var header = HttpHeader.with(key, values);

    if (header.isEmpty()) throw WrongHeaderException.err();

    return addHeader(header.get());
  }

  @Override
  public HttpResponse.Builder body(final String bodyAsString) {
    this.body = Optional.of(bodyAsString);
    this.bodyByte = Optional.of(bodyAsString.getBytes());
    return this;
  }

  @Override
  public HttpResponse.Builder body(final byte[] bodyAsByte) {
    this.body = Optional.of(new String(bodyAsByte));
    this.bodyByte = Optional.of(bodyAsByte);
    return this;
  }

  @Override
  public HttpResponse.Builder withContentType(final MimeTypes... types) {
    for (final MimeTypes type : types) {
      addHeader(StrUtils.CONTENT_TYPE, type.getValue());
    }

    return this;
  }

  @Override
  public HttpResponse.Builder withContentLength() {
    var length = String.valueOf(bodyByte.orElseGet(() -> new byte[0]).length);
    addHeader(StrUtils.CONTENT_LENGTH, length);
    return this;
  }

  @Override
  public HttpResponse build() {
    return new HttpResponse(
        httpVersion,
        httpStatus,
        httpHeaders,
        body,
        bodyByte
    );
  }
}
