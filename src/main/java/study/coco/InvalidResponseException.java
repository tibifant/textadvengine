package study.coco;

public class InvalidResponseException extends Exception {
  public InvalidResponseException() {
    super("The provided response didn't retrieve any results.");
  }
}
