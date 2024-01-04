package study.coco;

public class CmpItemResponseDecorator implements IResponseDecorator {
  private String item;
  private int cmpValue;

  public CmpItemResponseDecorator(String item, int cmpValue) {
    this.item = item;
    this.cmpValue = cmpValue;
  }

  @Override
  public void onChoose(GameState gameState) { }

  @Override
  public boolean isValid(GameState gameState) {
    return gameState.getItemCount(item) == cmpValue;
  }
}
