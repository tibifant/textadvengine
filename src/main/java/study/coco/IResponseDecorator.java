package study.coco;

public interface IResponseDecorator {
  public boolean isValid(GameState gameState) throws ScreenTransitionException;
  public void onChoose(GameState gameState);
}
