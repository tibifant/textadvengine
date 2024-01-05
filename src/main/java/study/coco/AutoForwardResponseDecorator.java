package study.coco;

import javax.naming.NameNotFoundException;

public class AutoForwardResponseDecorator implements IResponseDecorator {
  private String targetScreen;

  public AutoForwardResponseDecorator(String targetScreen) {
    this.targetScreen = targetScreen;
  }

  @Override
  public boolean isValid(GameState gameState) throws ScreenTransitionException {
    throw new ScreenTransitionException(targetScreen);
  }

  @Override
  public void onChoose(GameState gameState) {  }

  @Override
  public void validate(StaticObjectKeyValidator validator) throws NameNotFoundException {
    validator.validateScreenName(targetScreen);
  }
}
