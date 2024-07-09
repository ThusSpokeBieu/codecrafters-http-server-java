package com.github.gmessiasc.hermes4j.core.methods;

import com.github.gmessiasc.hermes4j.core.methods.exceptions.WrongMethodException;
import com.github.gmessiasc.hermes4j.utils.ObjectUtils;
import java.util.HashMap;

public enum HttpMethod {
  GET, POST, PUT, PATCH, DELETE, HEAD, CONNECT, OPTIONS, TRACE, ALL;

  private static final HashMap<String, HttpMethod> METHODS = new HashMap<>();

  static {
    METHODS.put("GET", GET);
    METHODS.put("POST", POST);
    METHODS.put("PUT", PUT);
    METHODS.put("PATCH", PATCH);
    METHODS.put("DELETE", DELETE);
    METHODS.put("HEAD", HEAD);
    METHODS.put("CONNECT", CONNECT);
    METHODS.put("OPTIONS", OPTIONS);
    METHODS.put("TRACE", TRACE);
    METHODS.put("ALL", TRACE);

  }

  public static HttpMethod get(final String str) {
    final var httpMethod = METHODS.get(str.toUpperCase());

    ObjectUtils.checkNull(httpMethod, WrongMethodException.err());

    return httpMethod;
  }

}
