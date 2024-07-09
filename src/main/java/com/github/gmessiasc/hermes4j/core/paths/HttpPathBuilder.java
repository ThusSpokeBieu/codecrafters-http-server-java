package com.github.gmessiasc.hermes4j.core.paths;

import com.github.gmessiasc.hermes4j.core.paths.exception.WrongPathException;
import com.github.gmessiasc.hermes4j.utils.StrUtils;
import java.util.Collections;
import java.util.HashMap;

public final class HttpPathBuilder {
  private HttpPathBuilder() {}
  private static final String SLASH = StrUtils.SLASH;

  public static HttpPath with(final String pathString) {
    validatePath(pathString);

    String[] paths = pathString.split(SLASH);
    return new HttpPath(paths, paths.length, Collections.emptyMap());
  }

  public static HttpPath with(final String pathString, final HashMap<String, Integer> pathParams) {
    validatePath(pathString);

    String[] paths = pathString.split(SLASH);
    return new HttpPath(paths, paths.length, pathParams);
  }

  private static void validatePath(final String pathString) {
    if (pathString.startsWith(SLASH)) {
      return;
    }

    throw WrongPathException.slashError();
  }
}
