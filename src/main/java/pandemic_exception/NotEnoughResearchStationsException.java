package pandemic_exception;

public class NotEnoughResearchStationsException extends RuntimeException {

  public NotEnoughResearchStationsException(String message) {
    super(message);
  }
}
