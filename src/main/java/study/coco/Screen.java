package study.coco;

import javax.naming.NameNotFoundException;
import java.util.List;

public class Screen {
  private ColoredTextElement text;
  private List<Response> responses;

  public Screen(ColoredTextElement text, List<Response> validResponses) {
    this.text = text;
    this.responses = validResponses;
  }

  public void activate(GameState gameState) throws ScreenTransitionException {
    for (var r : responses) {
      try {
        r.activate(gameState);
      } catch (ScreenTransitionException e) {
        r.performAction(gameState);
        throw e;
      }
    }
  }

  public void submitResponse(String response, GameState gameState) throws ScreenTransitionException, InvalidResponseException {
    boolean anyMatched = false;

    for (Response r : responses) {
      if (r.isValid(gameState) && r.keywordMatches(response)) {
        anyMatched = true;
        r.performAction(gameState);
        throw r.getGotoTargetScreenException(gameState);
      }
    }

    if (!anyMatched)
      throw new InvalidResponseException();
  }

  final String acceptAnyInputResponseIndicator = "#";
  public void print()
  {
    text.print();

    ColoredText.print("\n\n", Color.WHITE);

    int validResponseCount = 0;

    for (var r : responses) {
      if (r.isValid(null)) {
        if (r instanceof AcceptAnyInputResponse)
          r.setIndex(acceptAnyInputResponseIndicator);
        else
          r.setIndex(Integer.toString(++validResponseCount));

        r.print();
      }
    }
  }

  public void validate(StaticObjectKeyValidator validator) throws NameNotFoundException {
    for (var r : responses)
      r.validate(validator);
  }
}
