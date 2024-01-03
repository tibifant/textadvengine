package study.coco;

import java.util.Map;

public class Engine {

  private Map<String, Integer> stateVars;
  private Map<String, Item> items;
  private ScreenFactory screenFactory;
  private Screen activeScreen;

  public Engine() {
    screenFactory = new ScreenFactory();
  }

  // loadConfigData, loadAllScreens etc.

  public int getItemCount(String itemName) {
    return !items.containsKey(itemName) ? 0 : items.get(itemName).getCount();
  }

  public int getStateVarValue(String stateVar) {
    return !stateVars.containsKey(stateVar) ? 0 : stateVars.get(stateVar);
  }

  public void incrementItem(String itemName, int count) {
    if (!items.containsKey(itemName))
      items.put(itemName, new Item(new ColoredTextElement(itemName), count));
    else
      items.get(itemName).incrementCount(count);
  }

  public void setStateVar(String stateVar, int value) {
    if (!stateVars.containsKey(stateVar))
      stateVars.put(stateVar, value);
    else
      stateVars.replace(stateVar, value);
  }
}
