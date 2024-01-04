package study.coco;

public class AutoForwardResponseDecorator implements IResponseDecorator {
  String targetScreen;

  public AutoForwardResponseDecorator(String targetScreen) {
    this.targetScreen = targetScreen;
  }

  @Override
  public void onChoose(GameState gameState) {  }

  @Override
  public boolean isValid(GameState gameState) throws ScreenTransitionException {
    throw new ScreenTransitionException(targetScreen);
  }
}
