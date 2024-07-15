package com.github.gmessiasc.hermes4j.core.responses;

import com.github.gmessiasc.hermes4j.core.headers.HttpHeader;
import com.github.gmessiasc.hermes4j.core.headers.HttpStatus;
import com.github.gmessiasc.hermes4j.core.headers.HttpVersion;
import com.github.gmessiasc.hermes4j.core.headers.mime.MimeTypes;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public record HttpResponse(
    HttpVersion httpVersion,
    HttpStatus status,
    Map<String, Set<String>> headers,
    Optional<byte[]> body
) {

  public interface Builder {
    HttpResponse.Builder version(final HttpVersion version);
    HttpResponse.Builder status(final HttpStatus status);
    HttpResponse.Builder headers(final Map<String, Set<String>> headers);
    HttpResponse.Builder addHeader(final Map.Entry<String, Set<String>> headers);
    HttpResponse.Builder addHeader(final String string);
    HttpResponse.Builder addHeader(final String key, String... values);
    HttpResponse.Builder body(final String body);
    HttpResponse.Builder body(final byte[] body);
    HttpResponse.Builder withContentType(final MimeTypes... type);
    HttpResponse.Builder withContentLength();
    HttpResponse build();
  }
}
