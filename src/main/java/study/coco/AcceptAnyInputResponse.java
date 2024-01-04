package study.coco;

import java.util.List;

public class AcceptAnyInputResponse extends Response {
  public AcceptAnyInputResponse(List<IResponseDecorator> decorators, String targetScreen) {
    super("auto_forward_response", decorators, targetScreen, new ColoredTextElement("Press any Key"));
  }

  public boolean keywordMatches(String response) {
    return true;
  }
}
