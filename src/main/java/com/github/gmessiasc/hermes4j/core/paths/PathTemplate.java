package com.github.gmessiasc.hermes4j.core.paths;

import com.github.gmessiasc.hermes4j.core.endpoints.HttpEndpoint;
import com.github.gmessiasc.hermes4j.core.requests.HttpRequest;
import com.github.gmessiasc.hermes4j.utils.StrUtils;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public record PathTemplate(
    String path,
    Map<String, Integer> paths,
    Map<String, Integer> pathParams
) {
  private static final Logger logger = Logger.getLogger(PathTemplate.class.getName());

  private static final String SLASH = StrUtils.SLASH;

  public static PathTemplate with(final String pathString) {
    final String[] pathsArr = pathString.split(SLASH);
    final Map<String, Integer> paths = new HashMap<>();
    final Map<String, Integer> pathParams = new HashMap<>();

    for (int i = 0; i < pathsArr.length; i++) {
      final String currentPath = pathsArr[i];
      if (isPathParam(currentPath)) {
        putPathParam(currentPath, i, pathParams);
      } else {
        paths.put(currentPath, i);
      }
    }

    return new PathTemplate(
        pathString,
        paths,
        pathParams
    );
  }

  public boolean verify(final String[] pathString) {
    try {
      if (pathString.length == 0 && paths.isEmpty()) return true;
      if (pathString.length == 0 && !paths.isEmpty()) return false;
      if (paths.isEmpty() && pathParams.isEmpty() && pathString.length > 1) return false;

      boolean isValid = true;

      for (Map.Entry<String, Integer> entry : paths.entrySet()) {
        final String path = entry.getKey();
        final int index = entry.getValue();

        if (pathString.length < index) {
          isValid = false;
          break;
        }

        boolean equals = pathString[index].equalsIgnoreCase(path);

        if (!equals) {
          isValid = false;
          break;
        }
      }

      return isValid;

    } catch (final IndexOutOfBoundsException e) {
      return false;
    }
  }

  public boolean verify(final HttpPath httpPath) {
    return verify(httpPath.path());
  }

  public boolean verify(final HttpRequest request) {
    return verify(request.path());
  }

  public void extractParam(final HttpRequest request) {
    if(pathParams.isEmpty()) return;

    for (Map.Entry<String, Integer> entry : pathParams.entrySet()) {
      final String path = entry.getKey();
      final int index = entry.getValue();

      final String[] requestPath = request.path().path();
      final PathParams requestParams = request.pathParams();

      requestParams.addParam(path, requestPath[index]);


    }
  }

  private static void putPathParam(
      final String path,
      final int index,
      final Map<String, Integer> pathParams) {
    final var param = path.substring(1, path.length() - 1);
    pathParams.put(param, index);
  }

  private static boolean isPathParam(final String path) {
    return path.startsWith("{") && path.endsWith("}");
  }

  @Override
  public String toString() {
    return "PathTemplate{" +
        "path='" + path + '\'' +
        ", paths=" + paths +
        ", pathParams=" + pathParams +
        '}';
  }
}
