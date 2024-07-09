package com.github.gmessiasc.hermes4j.core.methods.exceptions;

import com.github.gmessiasc.hermes4j.shared.exceptions.NoStackTraceException;

public class WrongMethodException extends NoStackTraceException {
  private static final String MESSAGE = "Http method could not be inferred";
  private static final WrongMethodException ERROR = new WrongMethodException();

  public WrongMethodException() {
    super(MESSAGE);
  }

  public WrongMethodException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public WrongMethodException(final String message) {
    super(message);
  }

  public WrongMethodException(final Throwable cause) {
    super(MESSAGE, cause);
  }

  public static WrongMethodException err() {
    return ERROR;
  }
}
