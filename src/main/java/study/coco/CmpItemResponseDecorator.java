package study.coco;

public class CmpItemResponseDecorator implements IResponseDecorator {
  private String item;
  private int count;

  public CmpItemResponseDecorator(String item, int count) {
    this.item = item;
    this.count = count;
  }

  @Override
  public void onChoose(Engine engine) {

  }

  @Override
  public boolean isValid(Engine engine) {
    return engine.getItemCount(item) == count;
  }
}
