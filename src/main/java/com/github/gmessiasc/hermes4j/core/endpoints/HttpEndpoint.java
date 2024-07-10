package com.github.gmessiasc.hermes4j.core.endpoints;

import com.github.gmessiasc.hermes4j.core.codecs.Codec;
import com.github.gmessiasc.hermes4j.core.handlers.HttpHandler;
import com.github.gmessiasc.hermes4j.core.headers.HttpHeader;
import com.github.gmessiasc.hermes4j.core.headers.mime.MimeTypes;
import com.github.gmessiasc.hermes4j.core.methods.HttpMethod;
import com.github.gmessiasc.hermes4j.core.paths.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public record HttpEndpoint<I, O>(
    PathTemplate path,
    Set<HttpMethod> methods,
    List<HttpHeader> headers,
    HttpHandler handler,
    Codec codec
) {
  public interface Builder {
    Builder path(final String path);
    Builder methods(final HttpMethod... method);
    Builder methods(final String... method);
    Builder headers(final HttpHeader... headers);
    Builder addHeader(final HttpHeader header);
    Builder addHeader(final String key, String... values);
    Builder accept(final MimeTypes... mimeTypes);
    Builder handler(final HttpHandler aHandler);
    Builder codec(final Codec codec);
    HttpEndpoint build() throws IOException;
  }

  @Override
  public String toString() {
    return "HttpEndpoint{" +
        "path=" + path +
        ", methods=" + methods +
        ", headers=" + headers +
        ", handler=" + handler +
        ", codec=" + codec +
        '}';
  }
}
