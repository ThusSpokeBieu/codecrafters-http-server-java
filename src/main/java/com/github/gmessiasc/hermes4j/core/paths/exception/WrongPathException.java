package com.github.gmessiasc.hermes4j.core.paths.exception;

import com.github.gmessiasc.hermes4j.shared.exceptions.NoStackTraceException;

public class WrongPathException  extends NoStackTraceException {
  private static final String MESSAGE = "Path is not a valid one";
  private static final String SLASH_ERROR_MESSAGE = "Path should start with \"/\"";
  private static final WrongPathException ERROR = new WrongPathException();
  private static final WrongPathException SLASH_ERROR = new WrongPathException(SLASH_ERROR_MESSAGE);

  public WrongPathException() {
    super(MESSAGE);
  }

  public WrongPathException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public WrongPathException(final String message) {
    super(message);
  }

  public WrongPathException(final Throwable cause) {
    super(MESSAGE, cause);
  }

  public static WrongPathException err() {
    return ERROR;
  }

  public static WrongPathException slashError() {
    return SLASH_ERROR;
  }
}
