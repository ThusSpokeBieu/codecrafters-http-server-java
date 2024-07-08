public final class HttpUtils {
  private HttpUtils(){}

  public static final String HTTP = "HTTP";
  public static final String HTTP_VERSION = "1.1";
  public static final int STATUS_OK = 200;
  public static final String STATUSMESSAGE_OK = "OK";
  public static final String CRLF = "\r\n";

  public static final String OUTPUT = String.format("%s/%s %s %s%s%S", HTTP, HTTP_VERSION, STATUS_OK, STATUSMESSAGE_OK, CRLF, CRLF);
  public static final byte[] OUTPUT_BYTE = OUTPUT.getBytes();
}
