package exceptions;

public class WrongMethodException extends RuntimeException {
  private static final String MESSAGE = "Http method could not be inferred";
  private static final WrongMethodException ERROR = new WrongMethodException();

  public WrongMethodException() {
    super(MESSAGE, null, false, false);
  }

  public static WrongMethodException err() {
    return ERROR;
  }
}
