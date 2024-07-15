package com.github.gmessiasc.hermes4j.core.headers;

import com.github.gmessiasc.hermes4j.core.headers.exceptions.WrongHttpStatusException;
import com.github.gmessiasc.hermes4j.utils.ObjectUtils;
import java.util.HashMap;

public enum HttpStatus {
  OK(200, "OK"),
  CREATED(201, "Created"),
  BAD_REQUEST(400, "Bad Request"),
  NOT_FOUND(404, "Not Found");

  private final int status;
  private final byte[] statusBytes;
  private final String statusString;
  private final String message;
  private final String statusMessage;
  private final byte[] statusMessageBytes;


  private static final HashMap<Integer, HttpStatus> statusByCode = new HashMap<>(){};
  private static final HashMap<String, HttpStatus> statusByMessage = new HashMap<>(){};

  static {
    for (final HttpStatus value : HttpStatus.values()) {
      statusByCode.put(value.status, value);
      statusByMessage.put(value.message, value);
    }
  }

  HttpStatus(final int status, final String message) {
    this.status = status;
    this.message = message;
    this.statusString = String.valueOf(status);
    this.statusBytes = this.statusString.getBytes();
    this.statusMessage = String.format("%d %s", status, message);
    this.statusMessageBytes = statusMessage.getBytes();
  }

  public int getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

  public String getStatusMessage() {
    return statusMessage;
  }

  public byte[] getStatusBytes() {
    return statusBytes;
  }

  public String getStatusString() {
    return statusString;
  }

  public byte[] getStatusMessageBytes() {
    return statusMessageBytes;
  }

  public static HttpStatus get(final int code) {
    final var httpStatus = statusByCode.get(code);

    ObjectUtils.checkNull(httpStatus, WrongHttpStatusException.err());

    return httpStatus;
  }

  public static HttpStatus get(final String message) {
    final var httpStatus = statusByMessage.get(message);

    ObjectUtils.checkNull(httpStatus, WrongHttpStatusException.err());

    return httpStatus;
  }
}
