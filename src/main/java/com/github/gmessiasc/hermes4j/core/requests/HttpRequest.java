package com.github.gmessiasc.hermes4j.core.requests;

import com.github.gmessiasc.hermes4j.core.methods.HttpMethod;
import com.github.gmessiasc.hermes4j.core.paths.HttpPath;

public record HttpRequest(
    HttpMethod method,
    HttpPath path,
    String httpVersion) {

  public interface Builder {
    Builder method(final HttpMethod method);
    Builder path(final HttpPath path);
    Builder httpVersion(final String httpVersion);
    HttpRequest build();
  }


}
