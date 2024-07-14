package com.github.gmessiasc.hermes4j.core.codecs.compression;

import com.github.gmessiasc.hermes4j.core.codecs.compression.exceptions.WrongCompressionException;
import com.github.gmessiasc.hermes4j.core.requests.HttpRequest;
import com.github.gmessiasc.hermes4j.core.responses.HttpResponse;
import com.github.gmessiasc.hermes4j.utils.HeaderUtils;
import com.github.gmessiasc.hermes4j.utils.ObjectUtils;
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

  private static HttpCompression getCompressCodec(final String compressionString) {
    return switch(compressionString.toLowerCase()) {
      case "gzip" -> GzipCodec.INSTANCE;
      default -> null;
    };
  }

  public static HttpCompression get(final String compressionString) {
    var compression = getCompressCodec(compressionString);
    ObjectUtils.checkNull(compression, WrongCompressionException.err());
    return compression;
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
    HttpCompression compression = null;

    final var compressTypes = request
        .httpHeaders()
        .get(HeaderUtils.ACCEPT_ENCODING);

    for (final String compressType : compressTypes) {
      compression = getCompressCodec(compressType);

      if(compression != null) break;
    }

    ObjectUtils.checkNull(compression, WrongCompressionException.err());
    return compression;
  }

}
