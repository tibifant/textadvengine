package study.coco;

import javax.naming.NameNotFoundException;
import java.util.List;

public class CmpItemSumGreaterResponseDecorator implements IResponseDecorator {
  private List<String> items;
  private int cmpValue;

  public CmpItemSumGreaterResponseDecorator(List<String> items, int cmpValue) {
    this.items = items;
    this.cmpValue = cmpValue;
  }

  @Override
  public boolean isValid(GameState gameState) {
    int sum = 0;
    for (var i : items)
      sum += gameState.getItemCount(i);

    return sum > cmpValue;
  }

  @Override
  public void onChoose(GameState gameState) { }

  @Override
  public void validate(StaticObjectKeyValidator validator) throws NameNotFoundException {
    for (var i : items)
      validator.validateItemName(i);
  }
}
