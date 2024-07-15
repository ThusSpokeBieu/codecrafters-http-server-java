package com.github.gmessiasc.hermes4j.core.headers;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class HttpHeader {
  public static Optional<Map.Entry<String, Set<String>>> with(String key, String... values) {
    try {
      final var header = new AbstractMap.SimpleEntry<>(
          key,
          Set.of(values)
      );

      return Optional.of(header);
    } catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  public static Optional<Map.Entry<String, Set<String>>> with(String keyAndValue) {
    try {
      final String[] splittedKeyAndValue = keyAndValue.split(":\\s*");
      final String key = splittedKeyAndValue[0];
      final String values = splittedKeyAndValue[1];

      final boolean isInvalidKey = key == null || key.isEmpty();
      final boolean areInvalidValues = values == null || values.isEmpty();

      if(isInvalidKey || areInvalidValues) return Optional.empty();

      final String[] splittedValues = values.split(",\\s*");

      return with(key, splittedValues);
    } catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

}