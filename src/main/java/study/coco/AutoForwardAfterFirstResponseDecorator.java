package study.coco;

public class AutoForwardAfterFirstResponseDecorator implements IResponseDecorator {
  private String targetScreen;
  private int chosenCount = 0;

  public AutoForwardAfterFirstResponseDecorator(String targetScreen) {
    this.targetScreen = targetScreen;
  }

  @Override
  public void onChoose(GameState gameState) { }

  @Override
  public boolean isValid(GameState gameState) throws ScreenTransitionException {
    if (chosenCount != 0) {
      throw new ScreenTransitionException(targetScreen);
    } else {
      chosenCount++;
      return true;
    }
  }
}
