package com.github.gmessiasc.hermes4j.core.codecs.compression;

import com.github.gmessiasc.hermes4j.core.requests.HttpRequest;
import com.github.gmessiasc.hermes4j.core.responses.HttpResponse;
import com.github.gmessiasc.hermes4j.core.responses.HttpResponseBuilder;
import com.github.gmessiasc.hermes4j.utils.HeaderUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HexFormat;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public final class GzipCodec extends HttpCompression {
  private static final Logger logger = Logger.getLogger(GzipCodec.class.getName());

  public static GzipCodec INSTANCE = new GzipCodec();

  public GzipCodec() {
    super("gzip");
  }

  @Override
  public HttpRequest decode(final HttpRequest httpRequest) throws IOException {
    final var body = httpRequest.body();

    if (body.isEmpty()) return httpRequest;

    final InputStream inputStream = new ByteArrayInputStream(httpRequest.body().get().getBytes());
    final GZIPInputStream gzipStream = new GZIPInputStream(inputStream);
    final OutputStream baos = new ByteArrayOutputStream();

    baos.write(gzipStream.readAllBytes());

    final StringBuilder sb = new StringBuilder();
    sb.append(baos);

    return httpRequest.withBody(sb.toString());
  }

  @Override
  public HttpResponse encode(final HttpResponse response) throws IOException{
    if(response.body().isEmpty()) return response;

    final byte[] inputBytes = response.body().get();

    final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    final GZIPOutputStream gzipOs = new GZIPOutputStream(outputStream);

    gzipOs.write(inputBytes);
    gzipOs.close();

    return HttpResponseBuilder
        .builder()
        .status(response.status())
        .body(outputStream.toByteArray())
        .headers(response.headers())
        .version(response.httpVersion())
        .withContentLength()
        .addHeader(HeaderUtils.CONTENT_ENCODING, this.name)
        .build();
  }
}
