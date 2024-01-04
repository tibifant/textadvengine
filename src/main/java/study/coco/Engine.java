package study.coco;

import java.util.Map;

public class Engine {

  private ScreenFactory screenFactory;
  private Screen activeScreen;
  private GameState gameState;

  public Engine() {
    loadInitialGameState();
    screenFactory = new ScreenFactory();
  }

  private void loadInitialGameState() {
    gameState = new GameState();
    // TODO: !
  }

  public void clearScreen() {
    // TODO: !
  }

  public void printInvalidResponseError() {
    // TODO: !
  }

  public void loadScreen(String screenName)
  {
    // Handle meta-screens that automatically forward to the next screen without user input.
    while (true)
    {
      try {
        activeScreen = screenFactory.getScreen(screenName);
        activeScreen.activate(gameState);
        break;
      } catch (ScreenTransitionException e) {
        screenName = e.getTargetScreenName();
      }
    }

    clearScreen();
    activeScreen.print();
  }

  private String readResponse() {
    return ""; // TODO: !
  }

  public void update()
  {
    while (true) {
      try {
        activeScreen.submitResponse(readResponse(), gameState);
      }
      catch (ScreenTransitionException e) {
        loadScreen(e.getTargetScreenName());
      }
      catch (InvalidResponseException e) {
        printInvalidResponseError();
      }
    }
  }
}
