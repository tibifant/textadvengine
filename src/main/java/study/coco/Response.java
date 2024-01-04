package study.coco;

import java.util.List;

public class Response {
  private String keyword;
  private List<IResponseDecorator> decorators;
  private String targetScreen;
  private ColoredTextElement description;

  private boolean isValid = true;

  public Response(String keyword, List<IResponseDecorator> decorators, String targetScreen, ColoredTextElement description) {
    this.keyword = keyword;
    this.decorators = decorators;
    this.targetScreen = targetScreen;
    this.description = description;
  }

  public void activate(GameState gameState) throws ScreenTransitionException {
    isValid = false;

    for (var d : decorators)
      d.isValid(gameState);

    isValid = true;
  }

  public boolean isValid(GameState gameState) {
    return isValid;
  }

  public void performAction(GameState gameState) {
    for (var d : decorators)
      d.onChoose(gameState);
  }

  public boolean keywordMatches(String response) {
    return keyword == response;
  }

  public String getTargetScreenName() {
    return targetScreen;
  }

  public void print() {
    ColoredText.print(keyword, Color.Blue);

    if (!description.isEmpty()) {
      ColoredText.print(" - ", Color.White);
      description.print();
    }

    ColoredText.print("\n", Color.White);
  }
}
