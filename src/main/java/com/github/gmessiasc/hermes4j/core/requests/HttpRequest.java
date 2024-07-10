package com.github.gmessiasc.hermes4j.core.requests;

import com.github.gmessiasc.hermes4j.core.methods.HttpMethod;
import com.github.gmessiasc.hermes4j.core.paths.HttpPath;
import com.github.gmessiasc.hermes4j.core.paths.PathParams;

public record HttpRequest(
    HttpMethod method,
    HttpPath path,
    PathParams pathParams,
    String httpVersion) {

  public interface Builder {
    Builder method(final HttpMethod method);
    Builder path(final HttpPath path);
    Builder pathParams(final PathParams pathParams);
    Builder httpVersion(final String httpVersion);
    HttpRequest build();
  }


}
