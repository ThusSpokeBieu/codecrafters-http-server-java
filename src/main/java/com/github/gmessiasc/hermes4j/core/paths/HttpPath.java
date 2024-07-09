package com.github.gmessiasc.hermes4j.core.paths;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

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

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final HttpPath httpPath = (HttpPath) o;
    return length == httpPath.length && Objects.deepEquals(paths, httpPath.paths) && Objects.equals(pathParam, httpPath.pathParam);
  }

  @Override
  public int hashCode() {
    return Objects.hash(Arrays.hashCode(paths), length, pathParam);
  }

  @Override
  public String toString() {
    return "HttpPath{" +
        "paths=" + Arrays.toString(paths) +
        ", length=" + length +
        ", pathParam=" + pathParam +
        '}';
  }
}
