package com.github.gmessiasc.hermes4j.core.headers.exceptions;

import com.github.gmessiasc.hermes4j.shared.exceptions.NoStackTraceException;

public class WrongHeaderException extends NoStackTraceException {
  private static final String MESSAGE = "Couldn't build a correct header";
  private static final WrongHeaderException ERROR = new WrongHeaderException();

  public WrongHeaderException() {
    super(MESSAGE);
  }

  public WrongHeaderException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public WrongHeaderException(final String message) {
    super(message);
  }

  public WrongHeaderException(final Throwable cause) {
    super(MESSAGE, cause);
  }

  public static WrongHeaderException err() {
    return ERROR;
  }
}
