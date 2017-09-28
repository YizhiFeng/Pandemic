package pandemic_exception;

public class TooManyCardsInSameColorException extends RuntimeException {

  public TooManyCardsInSameColorException(String message) {
    super(message);
  }
}
