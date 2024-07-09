package com.github.gmessiasc.hermes4j.core.endpoints;

import com.github.gmessiasc.hermes4j.core.handlers.HttpHandler;
import com.github.gmessiasc.hermes4j.core.headers.HttpHeader;
import com.github.gmessiasc.hermes4j.core.headers.mime.MimeTypes;
import com.github.gmessiasc.hermes4j.core.methods.HttpMethod;
import com.github.gmessiasc.hermes4j.core.paths.HttpPath;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public record HttpEndpoint<I, O>(
    HttpPath path,
    Set<HttpMethod> methods,
    List<HttpHeader> headers,
    Optional<Type> bodyType,
    HttpHandler handler
) {
  public interface Builder {
    Builder path(final String path);
    Builder methods(final HttpMethod... method);
    Builder methods(final String... method);
    Builder headers(final HttpHeader... headers);
    Builder addHeader(final HttpHeader header);
    Builder addHeader(final String key, String... values);
    Builder accept(final MimeTypes... mimeTypes);
    Builder body(final Type body);
    Builder handler(final HttpHandler aHandler);
    HttpEndpoint build();
  }

  @Override
  public String toString() {
    return "HttpEndpoint{" +
        "path=" + path +
        ", methods=" + methods +
        ", headers=" + headers +
        ", bodyType=" + bodyType +
        ", handler=" + handler +
        '}';
  }
}
