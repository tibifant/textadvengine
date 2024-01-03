package study.coco;

import java.util.List;
import java.util.Map;

public class Response {
  private List<IResponseDecorator> decorators;
  private String targetScreen;
  private ColoredTextElement description;

  public Response(List<IResponseDecorator> decorators, String targetScreen, ColoredTextElement description) {
    this.decorators = decorators;
    this.targetScreen = targetScreen;
    this.description = description;
  }
}
