package study.coco;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Screen {
  private ColoredTextElement text;
  private List<Response> validResponses;

  public Screen(String text, List<Response> validResponses) {
    this.text = new ColoredTextElement(text);
    this.validResponses = validResponses;
  }
}
