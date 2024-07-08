import java.util.HashMap;

public enum HttpMethod {
  GET, POST, PUT, PATCH, DELETE, HEAD, CONNECT, OPTIONS, TRACE, WRONG_METHOD;

  private static final HashMap<String, HttpMethod> METHODS = new HashMap<>();

  static {
    METHODS.put("GET", GET);
    METHODS.put("POST", POST);
    METHODS.put("PUT", PUT);
    METHODS.put("PATCH", PATCH);
    METHODS.put("DELETE", DELETE);
    METHODS.put("HEAD", HEAD);
    METHODS.put("CONNECT", CONNECT);
    METHODS.put("OPTIONS", OPTIONS);
    METHODS.put("TRACE", TRACE);
  }

  public static HttpMethod get(final String str) {
    return METHODS.getOrDefault(str.toUpperCase(), WRONG_METHOD);
  }

}
