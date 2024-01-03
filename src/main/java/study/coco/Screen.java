package study.coco;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Screen {
  private List<ColoredText> text;
  private List<Response> validResponses;

  public Screen(List<ColoredText> text, List<Response> validResponses) {
    this.text = text;
    this.validResponses = validResponses;
  }
}
