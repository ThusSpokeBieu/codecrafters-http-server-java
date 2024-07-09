package com.github.gmessiasc.hermes4j.core.headers.mime;

public enum MimeTypes {
  ALL("*/*"),

  AAC("audio/aac"),
  MP3("audio/mpeg"),
  OPUS("audio/opus"),
  MIDI("audio/midi"),

  AVI("video/x-msvideo"),
  MP4("video/mp4"),
  MPEG("video/mpeg"),
  OGG_VIDEO("video/ogg"),

  PNG("image/png"),
  JPEG("image/jpeg"),
  GIF("image/gif"),
  APNG("image/apng"),
  AVIF("image/avif"),

  TEXT_PLAIN("text/plain"),
  HTML("text/html"),
  CSS("text/css"),
  JSON("application/json"),
  XML("application/xml"),
  PDF("application/pdf"),

  // Outros
  ZIP("application/zip"),
  EPUB("application/epub+zip"),
  JAVASCRIPT("text/javascript"),
  OTF("font/otf"),
  ICAL("text/calendar");

  private final String value;

  MimeTypes(final String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
