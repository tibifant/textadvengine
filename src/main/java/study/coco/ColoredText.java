package study.coco;

public class ColoredText {
  private String text;
  private Color color;

  public ColoredText(String text, Color color) {
    this.text = text;
    this.color = color;
  }

  public void print() {
    System.out.println(text);
  }
}
