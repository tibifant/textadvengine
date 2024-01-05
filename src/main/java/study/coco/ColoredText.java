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
    System.out.print("\u001B[" + (color.ordinal() < 8 ? (30 + color.ordinal()) : (90 - 8 + color.ordinal())) + "m");
    System.out.print(text);
    System.out.print("\u001B[0m");
  }
}
