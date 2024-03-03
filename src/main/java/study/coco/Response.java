package study.coco;

import javax.naming.NameNotFoundException;
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

    for (var d : decorators) {
      if (!d.isValid(gameState)) {
        isValid = false;
        return;
      }
    }

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
    return keyword.equals(response);
  }

  final String returnKeyword = "$return";

  public ScreenTransitionException getGotoTargetScreenException(GameState gameState) {
    if (targetScreen.equals(returnKeyword))
      return new ScreenTransitionException(gameState.popScreenName());
    else
      return new ScreenTransitionException(targetScreen);
  }

  public void print() {
    ColoredText.print(keyword, Color.BRIGHTBLUE);

    if (!description.isEmpty()) {
      ColoredText.print(" - ", Color.WHITE);
      description.print();
    }

    ColoredText.print("\n", Color.WHITE);
  }

  public void validate(StaticObjectKeyValidator validator) throws NameNotFoundException {
    if (!targetScreen.equals(returnKeyword))
      validator.validateScreenName(targetScreen);

    for (var d : decorators)
      d.validate(validator);
  }
}
