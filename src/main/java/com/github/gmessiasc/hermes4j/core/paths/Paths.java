package com.github.gmessiasc.hermes4j.core.paths;

import com.github.gmessiasc.hermes4j.utils.StrUtils;

public final class Paths {
  private Paths() {
  }

  public static final HttpPath HOME = HttpPathBuilder.with(StrUtils.SLASH);
}
