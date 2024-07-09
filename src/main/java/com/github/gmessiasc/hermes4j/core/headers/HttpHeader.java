package com.github.gmessiasc.hermes4j.core.headers;

public record HttpHeader(
    String key,
    String... values) {

  public static HttpHeader with(String key, String... values) {
    return new HttpHeader(key, values);
  }

}