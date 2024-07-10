package com.github.gmessiasc.hermes4j.core.headers;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

public record HttpHeader(
    String key,
    Set<String> values) {

  private static Logger logger = Logger.getLogger(HttpHeader.class.getName());

  public static HttpHeader with(String key, String... values) {
    return new HttpHeader(key, Set.of(values));
  }

  public static Optional<HttpHeader> with(String keyAndValue) {
    try {
      final String[] splittedKeyAndValue = keyAndValue.split(":\\s*");
      final String key = splittedKeyAndValue[0];
      final String values = splittedKeyAndValue[1];

      final boolean isInvalidKey = key == null || key.isEmpty();
      final boolean areInvalidValues = values == null || values.isEmpty();

      if(isInvalidKey || areInvalidValues) return Optional.empty();

      final String[] splittedValues = values.split(",\\s*");

      final var header = new HttpHeader(key, Set.of(splittedValues));

      return Optional.of(header);
    } catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final HttpHeader that = (HttpHeader) o;
    return Objects.equals(key, that.key);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(key);
  }
}