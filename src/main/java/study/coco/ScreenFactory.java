package study.coco;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class ScreenFactory {
  private Map<String, Screen> screens;

  public ScreenFactory() {
    mapScreens();
  }

  private void mapScreens() {
    try {
      InputStream inputStream = new FileInputStream(new File("assets/example.yaml"));
      Map<String, Object> allScreens = new Yaml().load(inputStream);

      for (var kvp : allScreens.entrySet())
        screens.put(kvp.getKey(), assembleScreenFromMap(kvp.getKey(), (Map)kvp.getValue()));

    } catch (Exception e) {
      System.out.println("Exception thrown: " + e.toString());
    }
  }

  private Screen assembleScreenFromMap(String screenName, Map<String, Object> parsedScreen) {
    return new Screen(stringToColoredText((String)parsedScreen.get("text")), assembleResponseListFromMap(screenName, (Map)parsedScreen.get("responses")));
  }

  private static List<ColoredText> stringToColoredText(String string) {
    List<ColoredText> text = new ArrayList<>();
    text.add(new ColoredText(string, Color.White));
    return text;
  }

  private static List<Response> assembleResponseListFromMap(String screenName, Map<String, Object> responses) {
    List<Response> validResponses = new ArrayList<>();
    for (var kvp : responses.entrySet())
      validResponses.add(assembleResponseFromMap(screenName, kvp.getKey(), (Map)kvp.getValue()));

    return validResponses;
  }

  private static Response assembleResponseFromMap(String screenName, String keyword, Map<String, Object> map) {
    String targetScreen = screenName; // may be overwritten later.


  }
}
