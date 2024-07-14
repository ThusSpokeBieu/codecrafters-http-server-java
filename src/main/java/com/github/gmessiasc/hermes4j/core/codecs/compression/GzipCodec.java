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
import java.util.Base64;
import java.util.HexFormat;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public final class GzipCodec extends HttpCompression {
  private static final Logger logger = Logger.getLogger(GzipCodec.class.getName());

  public static GzipCodec INSTANCE = new GzipCodec();
  private static Base64.Decoder DECODER = Base64.getDecoder();

  public GzipCodec() {
    super("gzip");
  }

  @Override
  public HttpRequest decode(final HttpRequest httpRequest) throws IOException {
    final var body = httpRequest.body();

    if (body.isEmpty()) return httpRequest;

    final byte[] compressedBytes = DECODER.decode(body.get());
    final InputStream inputStream = new ByteArrayInputStream(compressedBytes);
    final GZIPInputStream gzipStream = new GZIPInputStream(inputStream);
    final OutputStream baos = new ByteArrayOutputStream();

    logger.info("GZIPInputStream = " + gzipStream);

    baos.write(gzipStream.readAllBytes());
    logger.info("BAO = " + baos);

    final StringBuilder sb = new StringBuilder();
    sb.append(baos);

    logger.info("SB = " + sb);

    return httpRequest.withBody(sb.toString());
  }

  @Override
  public HttpResponse encode(final HttpResponse response) throws IOException{
    final byte[] inputBytes = response.bodyByte().orElse(new byte[0]);

    final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    final GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream);

    gzipOutputStream.write(inputBytes);

    final var hex = HexFormat.of().formatHex(outputStream.toByteArray());

    return HttpResponseBuilder
        .builder()
        .status(response.status())
        .body(hex)
        .headers(response.headers())
        .addHeader(HeaderUtils.CONTENT_ENCODING, this.name)
        .version(response.httpVersion())
        .withContentLength()
        .build();
  }
}
