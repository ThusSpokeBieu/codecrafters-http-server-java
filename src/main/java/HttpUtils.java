public final class HttpUtils {
  private HttpUtils(){}

  public static final String HTTP = "HTTP";
  public static final String HTTP_VERSION = "1.1";
  public static final String CRLF = "\r\n";
  public static final String HTTP_MESSAGE_TEMPLATE = "%s/%s %s %s%s%s";

  public static final int STATUS_OK = 200;
  public static final String STATUS_MESSAGE_OK = "OK";

  public static final int STATUS_NOT_FOUND = 404;
  public static final String STATUS_MESSAGE_NOT_FOUND = "Not Found";


  public static final String OK_200_MESSAGE = String.format(HTTP_MESSAGE_TEMPLATE, HTTP, HTTP_VERSION, STATUS_OK, STATUS_MESSAGE_OK, CRLF, CRLF);
  public static final byte[] OK_200_MESSAGE_BYTES = OK_200_MESSAGE.getBytes();

  public static final String NOT_FOUND_404_MESSAGE = String.format(HTTP_MESSAGE_TEMPLATE, HTTP, HTTP_VERSION, STATUS_NOT_FOUND, STATUS_MESSAGE_NOT_FOUND, CRLF, CRLF);
  public static final byte[] NOT_FOUND_404_MESSAGE_BYTES = NOT_FOUND_404_MESSAGE.getBytes();
}
