package com.github.gmessiasc.hermes4j.core.requests;

import com.github.gmessiasc.hermes4j.core.headers.HttpHeader;
import com.github.gmessiasc.hermes4j.core.headers.HttpVersion;
import com.github.gmessiasc.hermes4j.core.methods.HttpMethod;
import com.github.gmessiasc.hermes4j.core.paths.HttpPath;
import com.github.gmessiasc.hermes4j.core.paths.PathParams;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public record HttpRequest (
    HttpVersion httpVersion,
    HttpMethod method,
    HttpPath path,
    PathParams pathParams,
    Map<String, Set<String>> httpHeaders,
    boolean isCompressed,
    boolean hasAcceptedContent,
    Optional<byte[]> body
    ) {

  public HttpRequest withBody(final String body) {
    return new HttpRequest(
        httpVersion,
        method,
        path,
        pathParams,
        httpHeaders,
        isCompressed,
        hasAcceptedContent,
        Optional.of(body.getBytes())
    );
  }

  public interface Builder {
    Builder httpVersion(final HttpVersion httpVersion);
    Builder path(final HttpPath path);
    Builder method(final HttpMethod method);
    Builder pathParams(final PathParams pathParams);
    Builder header(final Map<String, Set<String>> header);
    Builder body(final String body);
    Builder body(final Optional<byte[]> body);
    Builder body(final byte[] body);
    HttpRequest build();
  }


}
