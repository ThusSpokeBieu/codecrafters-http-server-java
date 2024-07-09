package com.github.gmessiasc.hermes4j.utils;

import java.util.Objects;

public class ObjectUtils {

  public static void checkNull(Object object, RuntimeException error) {
    if (Objects.isNull(object)) {
      throw error;
    }
  }

  public static void checkNull(Object object, Throwable error) throws Throwable {
    if (Objects.isNull(object)) {
      throw error;
    }
  }
}
