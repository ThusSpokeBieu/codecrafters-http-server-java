package com.github.gmessiasc.hermes4j.core.paths;

import java.util.Arrays;
import java.util.Map;

public record HttpPath(
    String[] path,
    Map<String, Integer> pathParam
) {

  public interface Builder {
    Builder path(final String path);
    HttpPath build();
  }

  @Override
  public String toString() {
    return "HttpPath{" +
        "path=" + Arrays.toString(path) +
        ", pathParam=" + pathParam +
        '}';
  }
}
