package study.coco;

import java.util.ArrayList;
import java.util.List;

public class ColoredTextElement {
  private List<ColoredText> coloredText;

  public ColoredTextElement(String text) {
    coloredText = stringToColoredTextElement(text);
  }


  private static List<ColoredText> stringToColoredTextElement(String string) {
    List<ColoredText> text = new ArrayList<>();
    text.add(new ColoredText(string, Color.White));

    return text;
  }
}
