package com.github.gmessiasc.hermes4j.core.responses;

import com.github.gmessiasc.hermes4j.core.headers.HttpHeader;
import com.github.gmessiasc.hermes4j.core.headers.HttpStatus;
import com.github.gmessiasc.hermes4j.core.headers.HttpVersion;
import com.github.gmessiasc.hermes4j.core.headers.mime.MimeTypes;
import com.github.gmessiasc.hermes4j.utils.StrUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class HttpResponseBuilder implements HttpResponse.Builder {

  HttpVersion httpVersion = HttpVersion.HTTP_1_1;
  HttpStatus httpStatus = HttpStatus.OK;
  List<HttpHeader> httpHeaders = new ArrayList<>();
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
  public HttpResponseBuilder headers(final HttpHeader... headers) {
    httpHeaders.addAll(List.of(headers));
    return this;
  }

  @Override
  public HttpResponseBuilder addHeader(final HttpHeader header) {
    httpHeaders.add(header);
    return this;
  }

  @Override
  public HttpResponseBuilder addHeader(final String key, final String... values) {
    final var httpHeader = HttpHeader.with(key, values);
    return addHeader(httpHeader);
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
        Collections.unmodifiableList(httpHeaders),
        body,
        bodyByte
    );
  }
}
