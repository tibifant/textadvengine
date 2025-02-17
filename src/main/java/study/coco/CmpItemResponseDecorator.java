package study.coco;

import javax.naming.NameNotFoundException;

public class CmpItemResponseDecorator implements IResponseDecorator {
  private String item;
  private int cmpValue;

  public CmpItemResponseDecorator(String item, int cmpValue) {
    this.item = item;
    this.cmpValue = cmpValue;
  }

  @Override
  public boolean isValid(GameState gameState) {
    return gameState.getItemCount(item) == cmpValue;
  }

  @Override
  public void onChoose(GameState gameState) { }

  @Override
  public void validate(StaticObjectKeyValidator validator) throws NameNotFoundException {
    validator.validateItemName(item);
  }
}
