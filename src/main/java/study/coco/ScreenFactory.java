package study.coco;

import org.yaml.snakeyaml.Yaml;

import javax.naming.NameNotFoundException;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.InvalidParameterException;
import java.util.*;

public class ScreenFactory {
  private Map<String, Screen> screens;
  private int messageStateCount = 0;

  public ScreenFactory() {
    screens = new HashMap<>();

    File assetsDirectory = new File("assets");

    for (var file : assetsDirectory.listFiles())
      if (file.getPath().endsWith(".yaml") && !file.getAbsolutePath().endsWith(File.separator + "config.yaml"))
        parseScreenFromFile(file);
  }

  private void parseScreenFromFile(File file) {
    try {
      System.out.println("Parsing file '" + file.getAbsolutePath() + "'.");
      InputStream inputStream = new FileInputStream(file);
      Map<String, Object> allScreens = new Yaml().load(inputStream);

      for (var kvp : allScreens.entrySet())
        screens.put(kvp.getKey(), parseScreen(kvp.getKey(), (Map)kvp.getValue()));

    } catch (Exception e) {
      System.out.println("Failed to parse Screens from '" + file.getAbsolutePath() + "' with Exception: " + e.toString());
      e.printStackTrace();
    }
  }

  private Screen parseScreen(String screenName, Map<String, Object> parsedScreen) {
    String text = "\n";
    List<Response> responses = new ArrayList<>();

    for (var item : parsedScreen.entrySet()) {
      switch (item.getKey())
      {
        case "text": text += (String)item.getValue(); break;
        case "responses":
          for (var r : ((List)item.getValue())) {
            var entry = (Map.Entry<String, List>)((Map)r).entrySet().stream().findFirst().get();
            responses.add(parseResponse(screenName, entry.getKey(), entry.getValue()));
          }
          break;
        default: throw new InvalidParameterException("Unexpected key: '" + item.getKey() + "'.");
      }
    }

    return new Screen(new ColoredTextElement(text), responses);
  }

  final String GotoMessageStateAttribute = "goto_message_state";
  final String GotoMessageStateOnceAttribute = "goto_message_state_once";
  final String GotoMessageStateAttributeTargetState = "target_state";

  private Response parseResponse(String screenName, String responseKeyword, List responseContents) {
    List<IResponseDecorator> decorators = new ArrayList<>();

    String targetScreen = screenName; // may be overwritten later.
    String descriptionText = "";
    boolean isAcceptAnyResponse = false;

    for (var value : responseContents) {
      if (value instanceof String) { // single string decorator names.
        switch ((String)value) {
          case "accept_any_input": isAcceptAnyResponse = true; break;
          default: throw new InvalidParameterException("Unexpected single string decorator name: '" + (String)value + "'.");
        }
      } else {
        var containedValues = ((Map) value);
        if (containedValues.size() == 1) { // single key decorators (most of them).
          var item = (Map.Entry<String, Object>) (containedValues.entrySet().stream().findFirst().get());
          switch (item.getKey()) {
            case "goto":
              targetScreen = (String) item.getValue();
              break;
            case "description":
              descriptionText = (String) item.getValue();
              break;
            case GotoMessageStateAttribute:
              targetScreen = createMessageScreen((String)item.getValue(), screenName);
              break;
            case GotoMessageStateOnceAttribute:
              targetScreen = createMessageScreen((String)item.getValue(), screenName, true);
              break;
            default:
              decorators.add(parseDecorator(screenName, item.getKey(), item.getValue()));
          }
        } else { // multi-key decorators.
          if (containedValues.containsKey(GotoMessageStateAttribute) || containedValues.containsKey(GotoMessageStateOnceAttribute)) {
            String message;

            if (containedValues.containsKey(GotoMessageStateAttribute))
              message = (String)containedValues.get(GotoMessageStateAttribute);
            else
              message = (String)containedValues.get(GotoMessageStateOnceAttribute);

            String messageScreenTarget = screenName;

            if (containedValues.containsKey(GotoMessageStateAttributeTargetState))
              messageScreenTarget = (String)containedValues.get(GotoMessageStateAttributeTargetState);

            targetScreen = createMessageScreen(message, messageScreenTarget, containedValues.containsKey(GotoMessageStateOnceAttribute));
          } else {
            throw new InvalidParameterException("Unexpected attribute in " + screenName + ": '" + containedValues + "'.");
          }
        }
      }
    }

    if (isAcceptAnyResponse)
      return new AcceptAnyInputResponse(decorators, targetScreen);
    else
      return new Response(responseKeyword, decorators, targetScreen, new ColoredTextElement(descriptionText));
  }

  private IResponseDecorator parseDecorator(String screenName, String decoratorName, Object decoratorContents) {
    switch (decoratorName) {
      case "only_visible_if_item_equals": {
        List contents = (List) decoratorContents;
        String itemName = (String) contents.get(0);
        int cmpValue = (Integer) contents.get(1);
        return new CmpItemResponseDecorator(itemName, cmpValue);
      }
      case "only_visible_if_item_sum_greater": {
        List contents = (List)decoratorContents;
        int cmpValue = (Integer) contents.remove(contents.size() - 1);
        List<String> itemNames = (List<String>) contents;
        return new CmpItemSumGreaterResponseDecorator(itemNames, cmpValue);
      }
      case "only_visible_if_item_sum_smaller": {
        List contents = (List)decoratorContents;
        int cmpValue = (Integer) contents.remove(contents.size() - 1);
        List<String> itemNames = (List<String>) contents;
        return new CmpItemSumSmallerResponseDecorator(itemNames, cmpValue);
      }
      case "give_item": {
        if (decoratorContents instanceof String) {
          String itemName = (String) decoratorContents;
          return new GiveItemResponseDecorator(itemName, 1);
        } else if (decoratorContents instanceof List) {
          List contents = (List)decoratorContents;
          String itemName = (String)contents.get(0);
          int addedItemCount = (Integer)contents.get(1);
          return new GiveItemResponseDecorator(itemName, addedItemCount);
        }
        throw new InvalidParameterException("Unexpected type for decorator '" + decoratorName + "': " + decoratorContents.getClass().getName() + ".");
      }
      case "remove_item": {
        if (decoratorContents instanceof String) {
          String itemName = (String) decoratorContents;
          return new RemoveItemResponseDecorator(itemName, 1);
        } else if (decoratorContents instanceof List) {
          List contents = (List)decoratorContents;
          String itemName = (String)contents.get(0);
          int subtractedItemCount = (Integer)contents.get(1);
          return new GiveItemResponseDecorator(itemName, subtractedItemCount);
        }
        throw new InvalidParameterException("Unexpected type for decorator '" + decoratorName + "': " + decoratorContents.getClass().getName() + ".");
      }
      case "only_visible_if_state_var_equals": {
        List contents = (List)decoratorContents;
        String stateVarName = (String) contents.get(0);
        int cmpValue = (Integer) contents.get(1);
        return new CmpStateVarResponseDecorator(stateVarName, cmpValue);
      }
      case "set_state_var": {
        List contents = (List)decoratorContents;
        String stateVarName = (String)contents.get(0);
        int stateVarValue = (Integer)contents.get(1);
        return new SetStateVarResponseDecorator(stateVarName, stateVarValue);
      }
      case "auto_forward_to": {
        String targetScreen = (String)decoratorContents;
        return new AutoForwardResponseDecorator(targetScreen);
      }
      default: {
        throw new InvalidParameterException("Unexpected decorator name: '" + decoratorName + "'.");
      }
    }
  }

  private String createMessageScreen(String message, String targetState) {
    return createMessageScreen(message, targetState, false);
  }

  private String createMessageScreen(String message, String targetState, boolean showOnlyOnce) {
    List<IResponseDecorator> decorators = new ArrayList<>();
    if (showOnlyOnce)
      decorators.add(new AutoForwardAfterFirstResponseDecorator(targetState));

    List<Response> responses = new ArrayList<>();
    responses.add(new AcceptAnyInputResponse(decorators, targetState));

    String stateName = "message_state_" + messageStateCount++;
    screens.put(stateName, new Screen(new ColoredTextElement(message), responses));

    return stateName;
  }

  public void validate(GameState gameState) throws NameNotFoundException {
    StaticObjectKeyValidator validator = new StaticObjectKeyValidator(gameState, screens.keySet());
    
    for (var screen : screens.values())
      screen.validate(validator);
  }

  public Screen getScreen(String screenName) {
    return screens.get(screenName);
  }
}
