package study.coco;

public class Item {
  private ColoredTextElement descritpion;
  private int count;

  public Item(ColoredTextElement description, int count) {
    this.descritpion = description;
    this.count = count;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }
}
