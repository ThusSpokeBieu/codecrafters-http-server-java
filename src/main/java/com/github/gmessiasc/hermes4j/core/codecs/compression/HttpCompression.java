package com.github.gmessiasc.hermes4j.core.codecs.compression;

import com.github.gmessiasc.hermes4j.core.codecs.compression.exceptions.WrongCompressionException;
import com.github.gmessiasc.hermes4j.core.requests.HttpRequest;
import com.github.gmessiasc.hermes4j.core.responses.HttpResponse;
import com.github.gmessiasc.hermes4j.utils.HeaderUtils;
import java.io.IOException;

public abstract sealed class HttpCompression permits GzipCodec {
  public final String name;

  protected HttpCompression(final String name) {
    this.name = name;
  }

  public abstract HttpRequest decode(final HttpRequest request) throws IOException;
  public abstract HttpResponse encode(final HttpResponse response) throws IOException;

  public String getName() {
    return name;
  }

  public static HttpCompression get(final String compressionString) {
    return switch(compressionString.toLowerCase()) {
      case "gzip" -> GzipCodec.INSTANCE;
      default -> throw WrongCompressionException.err();
    };
  }

  public static HttpCompression get(final HttpRequest request) {
    final var compressType = request
        .httpHeaders()
        .get(HeaderUtils.CONTENT_ENCODING)
        .stream()
        .findAny()
        .get();

    return get(compressType);
  }

  public static HttpCompression getAccepted(final HttpRequest request) {
    final var compressType = request
        .httpHeaders()
        .get(HeaderUtils.ACCEPT_ENCODING)
        .stream()
        .findAny()
        .get();

    return get(compressType);
  }

}
