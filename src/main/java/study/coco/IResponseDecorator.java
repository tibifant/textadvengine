package study.coco;

import javax.naming.NameNotFoundException;

public interface IResponseDecorator {
  public boolean isValid(GameState gameState) throws ScreenTransitionException;
  public void onChoose(GameState gameState);

  public void validate(StaticObjectKeyValidator validator) throws NameNotFoundException;
}
