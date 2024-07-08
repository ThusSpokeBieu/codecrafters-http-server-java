import exceptions.WrongMethodException;
import java.util.Objects;

public class HttpRequest {
  private final HttpMethod method;
  private final String path;
  private final String httpVersion;

  public HttpRequest(
      final HttpMethod method,
      final String path,
      final String httpVersion
  ) {
    this.method = method;
    this.path = path;
    this.httpVersion = httpVersion;
  }

  public static HttpRequest with(final String[] str) {
    final var method = HttpMethod.get(str[0]);
    final var path = str[1];
    final var httpVersion = str[2];

    if (method.equals(HttpMethod.WRONG_METHOD)) {
      throw WrongMethodException.err();
    }

    return new HttpRequest(method, path, httpVersion);
  }

  public HttpMethod getMethod() {
    return method;
  }

  public String getPath() {
    return path;
  }

  public String getHttpVersion() {
    return httpVersion;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    HttpRequest that = (HttpRequest) o;
    return method == that.method && Objects.equals(path, that.path) && Objects.equals(httpVersion, that.httpVersion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(method, path, httpVersion);
  }


  @Override
  public String toString() {
    return "HttpRequest{" +
        "method=" + method +
        ", path='" + path + '\'' +
        ", httpVersion='" + httpVersion + '\'' +
        '}';
  }
}
