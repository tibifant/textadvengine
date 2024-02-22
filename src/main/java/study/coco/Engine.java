package study.coco;

import org.yaml.snakeyaml.Yaml;

import javax.naming.NameNotFoundException;
import java.io.*;
import java.util.List;
import java.util.Map;

public class Engine {

  private ScreenFactory screenFactory;
  private Screen activeScreen;
  private GameState gameState;
  private BufferedReader inputReader;

  public Engine() {
    inputReader = new BufferedReader(new InputStreamReader(System.in));

    screenFactory = new ScreenFactory();
    loadInitialGameState();

    // optional.
    try {
      screenFactory.validate(gameState);
    } catch (NameNotFoundException e) {
      System.out.println(e.getMessage());
      System.exit(-1);
    }
  }

  private void loadInitialGameState() {
    gameState = new GameState();

    // Parse Config File.
    try {
      Yaml yaml = new Yaml();
      Map<String, Object> configData = yaml.load(new FileInputStream(new File("assets/config.yaml")));

      // Items
      for (var element : ((List)configData.get("items"))) {
        var item = ((Map<String, Integer>)element).entrySet().stream().findFirst().get();
        gameState.setItemCount(item.getKey(), item.getValue());
      }

      // StateVars
      for (var element : ((List)configData.get("state_vars"))) {
        var item = ((Map<String, Integer>)element).entrySet().stream().findFirst().get();
        gameState.setStateVar(item.getKey(), item.getValue());
      }

      loadScreen((String) configData.get("initial_screen"));
    } catch (Exception e) {
      System.out.println("Failed to parse config file with Exception '" + e.toString() + "'");
      e.printStackTrace();
    }
  }

  public void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  public void printInvalidResponseError() {
    ColoredText.print("Invalid Response.\n", Color.BRIGHTRED);
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
    try {
      return inputReader.readLine();
    } catch (IOException e) {
      System.out.println("AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH!");
      e.printStackTrace();
      System.out.flush();
      System.exit(-1);
      return "yeah, sure, we'll return after System.exit, because that's definitely a thing.";
    }
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
