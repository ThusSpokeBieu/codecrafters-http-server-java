package com.github.gmessiasc.hermes4j.shared.exceptions;

public abstract class NoStackTraceException extends RuntimeException {
  protected NoStackTraceException(final String message, final Throwable cause) {
    super(message, cause, false, false);
  }

  protected NoStackTraceException(final String message) {
    super(message, null, false, false);
  }

}
