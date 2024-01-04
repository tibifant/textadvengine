package study.coco;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public class Engine {

  private ScreenFactory screenFactory;
  private Screen activeScreen;
  private GameState gameState;

  public Engine() {
    screenFactory = new ScreenFactory();
    loadInitialGameState();
  }

  private void loadInitialGameState() {
    gameState = new GameState();

    // Parse Config File.
    try {
      Yaml yaml = new Yaml();
      Map<String, Object> configData = yaml.load(new FileInputStream(new File("assets/config.yaml")));

      for (var element : ((List)configData.get("items"))) {
        var item = ((Map<String, Integer>)element).entrySet().stream().findFirst().get();
        gameState.setItemCount(item.getKey(), item.getValue());
      }

      loadScreen((String) configData.get("initial_screen"));
    } catch (Exception e) {
      System.out.println("Failed to parse config file with Exception '" + e.toString() + "'");
      e.printStackTrace();
    }
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
