package com.github.gmessiasc.hermes4j.core.codecs.compression;

import com.github.gmessiasc.hermes4j.core.headers.HttpHeader;
import com.github.gmessiasc.hermes4j.core.requests.HttpRequest;
import com.github.gmessiasc.hermes4j.core.responses.HttpResponse;
import com.github.gmessiasc.hermes4j.core.responses.HttpResponseBuilder;
import com.github.gmessiasc.hermes4j.utils.HeaderUtils;
import com.github.gmessiasc.hermes4j.utils.HttpUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public final class GzipCodec extends HttpCompression {
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

    baos.write(gzipStream.readAllBytes());

    return httpRequest.withBody(baos.toString());
  }

  @Override
  public HttpResponse encode(final HttpResponse response) throws IOException{
    final byte[] inputBytes = response.bodyByte().orElse(new byte[0]);

    final OutputStream outputStream = new ByteArrayOutputStream();
    final GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream);
    gzipOutputStream.write(inputBytes);

    return HttpResponseBuilder
        .builder()
        .status(response.status())
        .body(gzipOutputStream.toString())
        .headers(response.headers())
        .addHeader(HeaderUtils.CONTENT_ENCODING, this.name)
        .version(response.httpVersion())
        .withContentLength()
        .build();
  }
}
