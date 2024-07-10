package com.github.gmessiasc.hermes4j.core.paths.exception;

import com.github.gmessiasc.hermes4j.shared.exceptions.NoStackTraceException;

public class PathParamTypeException extends NoStackTraceException {
  protected PathParamTypeException(final String message, final Throwable cause) {
    super(message, cause);
  }

  protected PathParamTypeException(final String message) {
    super(message);
  }

  public static PathParamTypeException INVALID_TYPE() {
    return new PathParamTypeException("The object is a invalid type");
  }

  public static PathParamTypeException INVALID_TYPE(final Throwable cause) {
    return new PathParamTypeException("The object is a invalid type", cause);
  }

  public static PathParamTypeException INVALID_STRING() {
    return new PathParamTypeException("The object is not a valid string");
  }

  public static PathParamTypeException INVALID_STRING(final Throwable cause) {
    return new PathParamTypeException("The object is not a valid string", cause);
  }

  public static PathParamTypeException INVALID_INTEGER() {
    return new PathParamTypeException("The object is not a valid integer");
  }

  public static PathParamTypeException INVALID_INTEGER(final Throwable cause) {
    return new PathParamTypeException("The object is not a valid integer", cause);
  }

  public static PathParamTypeException INVALID_LONG() {
    return new PathParamTypeException("The object is not a valid long");
  }

  public static PathParamTypeException INVALID_LONG(final Throwable cause) {
    return new PathParamTypeException("The object is not a valid long", cause);
  }

  public static PathParamTypeException INVALID_FLOAT() {
    return new PathParamTypeException("The object is not a valid float");
  }

  public static PathParamTypeException INVALID_FLOAT(final Throwable cause) {
    return new PathParamTypeException("The object is not a valid float", cause);
  }

  public static PathParamTypeException INVALID_DOUBLE() {
    return new PathParamTypeException("The object is not a valid double");
  }

  public static PathParamTypeException INVALID_DOUBLE(final Throwable cause) {
    return new PathParamTypeException("The object is not a valid double", cause);
  }

  public static PathParamTypeException OBJECT_IS_NULL() {
    return new PathParamTypeException("The object is null");
  }

  public static PathParamTypeException OBJECT_IS_NULL(final Throwable cause) {
    return new PathParamTypeException("The object is null", cause);
  }


}
