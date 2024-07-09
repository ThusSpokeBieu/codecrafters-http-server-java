package com.github.gmessiasc.hermes4j.core.headers.exceptions;

import com.github.gmessiasc.hermes4j.shared.exceptions.NoStackTraceException;

public class WrongHttpStatusException extends NoStackTraceException {
  private static final String MESSAGE = "Http status does not exists";
  private static final WrongHttpStatusException ERROR = new WrongHttpStatusException();

  public WrongHttpStatusException() {
    super(MESSAGE);
  }

  public WrongHttpStatusException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public WrongHttpStatusException(final String message) {
    super(message);
  }

  public WrongHttpStatusException(final Throwable cause) {
    super(MESSAGE, cause);
  }

  public static WrongHttpStatusException err() {
    return ERROR;
  }
}
