package com.github.gmessiasc.hermes4j.core.responses;

import com.github.gmessiasc.hermes4j.core.headers.HttpStatus;
import com.github.gmessiasc.hermes4j.core.headers.mime.MimeTypes;

public class Responses {
  public final static HttpResponse OK = HttpResponseBuilder
      .builder()
      .status(HttpStatus.OK)
      .withContentType(MimeTypes.TEXT_PLAIN)
      .withContentLength()
      .build();

  public final static HttpResponse NOT_FOUND = HttpResponseBuilder
      .builder()
      .status(HttpStatus.NOT_FOUND)
      .build();
}
