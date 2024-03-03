package study.coco;

import javax.naming.NameNotFoundException;

public class PushScreenNameToStackDecorator implements IResponseDecorator {
  private String screenName;

  public PushScreenNameToStackDecorator(String screenName) {
    this.screenName = screenName;
  }

  @Override
  public boolean isValid(GameState gameState) {
    return true;
  }

  @Override
  public void onChoose(GameState gameState) {
    gameState.pushScreenName(screenName);
  }

  @Override
  public void validate(StaticObjectKeyValidator validator) throws NameNotFoundException {
    validator.validateScreenName(screenName);
  }
}
