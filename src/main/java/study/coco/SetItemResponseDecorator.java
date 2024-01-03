package study.coco;

public class SetItemResponseDecorator implements IResponseDecorator {
  private String item;
  private int count;

  public SetItemResponseDecorator(String item, int count) {
    this.item = item;
    this.count = count;
  }

  @Override
  public void onChoose(Engine engine) {
    engine.incrementItem(item, count);
  }

  @Override
  public boolean isValid(Engine engine) {
    return true;
  }
}
