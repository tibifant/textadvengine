package study.coco;

public class ColoredText {
  private String text;
  private Color color;

  public ColoredText(String text, Color color) {
    this.text = text;
    this.color = color;
  }

  public void print() {
    print(text, color);
  }

  public boolean isEmpty() { return text.isEmpty(); }

  public static void print(String text, Color color) {
    System.out.print(text);
  }
}
