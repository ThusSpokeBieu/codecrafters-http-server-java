package com.github.gmessiasc.hermes4j.core.codecs.compression.exceptions;

import com.github.gmessiasc.hermes4j.shared.exceptions.NoStackTraceException;

public class WrongCompressionException extends NoStackTraceException {
  private static final String MESSAGE = "Compression is not valid in this server";
  private static final WrongCompressionException ERROR = new WrongCompressionException();

  public WrongCompressionException() {
    super(MESSAGE);
  }

  public WrongCompressionException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public WrongCompressionException(final String message) {
    super(message);
  }

  public WrongCompressionException(final Throwable cause) {
    super(MESSAGE, cause);
  }

  public static WrongCompressionException err() {
    return ERROR;
  }
}