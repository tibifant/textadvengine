package study.coco;

import java.util.ArrayList;
import java.util.List;

public class ColoredTextElement {
  private List<ColoredText> text;

  public ColoredTextElement(String text) {
    this.text = stringToColoredTextElement(text);
  }


  private static List<ColoredText> stringToColoredTextElement(String string) {
    List<ColoredText> text = new ArrayList<>();
    text.add(new ColoredText(string, Color.WHITE));

    return text;
  }

  public void print() {
    for (var t : text)
      t.print();
  }

  public boolean isEmpty() {
    return text.size() == 0 || (text.size() == 1 && text.get(0).isEmpty());
  }
}
