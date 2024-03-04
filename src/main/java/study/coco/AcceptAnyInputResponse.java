package study.coco;

import java.util.List;

public class AcceptAnyInputResponse extends Response {
  public AcceptAnyInputResponse(List<IResponseDecorator> decorators, String targetScreen) {
    super("", "0", decorators, targetScreen, new ColoredTextElement("Press [Enter] to continue."));
  }

  public boolean keywordMatches(String response) {
    return true;
  }
}
