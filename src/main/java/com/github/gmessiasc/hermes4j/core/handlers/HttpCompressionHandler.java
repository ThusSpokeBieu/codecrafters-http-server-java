package com.github.gmessiasc.hermes4j.core.handlers;

import com.github.gmessiasc.hermes4j.core.codecs.compression.HttpCompression;
import com.github.gmessiasc.hermes4j.core.codecs.compression.exceptions.WrongCompressionException;
import com.github.gmessiasc.hermes4j.core.requests.HttpRequest;
import com.github.gmessiasc.hermes4j.core.responses.HttpResponse;
import java.io.IOException;

public class HttpCompressionHandler {

  public static HttpRequest decompress(final HttpRequest request) throws IOException {
    if (!request.isCompressed()) return request;
    HttpCompression compresser = HttpCompression.get(request);
    return compresser.decode(request);
  }

  public static HttpResponse compress(final HttpResponse response, final HttpRequest request) throws IOException {
    try {
      HttpCompression compresser = HttpCompression.getAccepted(request);
      return compresser.encode(response);
    } catch (final WrongCompressionException e) {
      return response;
    }
  }
}
