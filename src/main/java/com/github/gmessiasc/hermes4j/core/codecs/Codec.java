package com.github.gmessiasc.hermes4j.core.codecs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Codec<I, O> {
  I decode(final InputStream inputStream);
  void encode(final OutputStream outputStream, final O response) throws IOException;
}

