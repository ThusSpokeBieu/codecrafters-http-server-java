package com.github.gmessiasc.hermes4j.core.codecs;

import java.io.IOException;
import java.net.Socket;

public interface Codec<I, O> {
  I decode(final Socket socket);
  void encode(final Socket socket, final O response) throws IOException;
}
