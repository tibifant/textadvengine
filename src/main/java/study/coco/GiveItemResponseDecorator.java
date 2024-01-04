package study.coco;

public class GiveItemResponseDecorator implements IResponseDecorator {
  private String item;
  private int count;

  public GiveItemResponseDecorator(String item, int count) {
    this.item = item;
    this.count = count;
  }

  @Override
  public void onChoose(GameState gameState) {
    gameState.setItemCount(item, gameState.getItemCount(item) + count);
  }

  @Override
  public boolean isValid(GameState gameState) {
    return true;
  }
}
