package com.github.gmessiasc.hermes4j.core.paths;

import java.util.Map;

public record HttpPath(
    String[] paths,
    int length,
    Map<String, Integer> pathParam
) {

  public interface Builder {
    Builder addPaths(final String[] paths);
    Builder addPath(final String path);
    Builder addPathWithParam(final String path, String param);
    HttpPath build();
  }
}
