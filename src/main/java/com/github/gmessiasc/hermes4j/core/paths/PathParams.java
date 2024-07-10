package com.github.gmessiasc.hermes4j.core.paths;

import com.github.gmessiasc.hermes4j.core.paths.exception.PathParamTypeException;
import com.github.gmessiasc.hermes4j.utils.ObjectUtils;
import java.util.HashMap;
import java.util.Map;

public class PathParams {
  private final Map<String, String> params = new HashMap<>();

  public void addParam(
      final String paramKey,
      final String paramValue) {
    params.put(paramKey, paramValue);
  }

  public Object getObject(final String paramKey) {
    return params.get(paramKey);
  }

  public String getString(final String paramKey) {
    final var obj = params.get(paramKey);
    ObjectUtils.checkNull(obj, PathParamTypeException.OBJECT_IS_NULL());
    return String.valueOf(obj);
  }

  public Integer getInteger(final String paramKey) {
    final String str = getString(paramKey);

    try {
      return Integer.valueOf(str);
    } catch (NumberFormatException e) {
      throw PathParamTypeException.INVALID_INTEGER(e);
    }
  }

  public Long getLong(final String paramKey) {
    final String str = getString(paramKey);

    try {
      return Long.valueOf(str);
    } catch (NumberFormatException e) {
      throw PathParamTypeException.INVALID_LONG(e);
    }
  }

  public Double getDouble(final String paramKey) {
    final String str = getString(paramKey);

    try {
      return Double.valueOf(str);
    } catch (NumberFormatException e) {
      throw PathParamTypeException.INVALID_LONG(e);
    }
  }

  public Float getFloat(final String paramKey) {
    final String str = getString(paramKey);

    try {
      return Float.valueOf(str);
    } catch (NumberFormatException e) {
      throw PathParamTypeException.INVALID_LONG(e);
    }
  }

  @Override
  public String toString() {
    return "PathParams{" +
        "params=" + params +
        '}';
  }
}
