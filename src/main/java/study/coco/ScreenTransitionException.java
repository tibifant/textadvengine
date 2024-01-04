package study.coco;

public class ScreenTransitionException extends Exception {
  private String targetScreen;

  public ScreenTransitionException(String targetScreen) {
    super("Requested screen transition to " + targetScreen + ".");
    this.targetScreen = targetScreen;
  }

  public String getTargetScreenName() { return targetScreen; }
}
