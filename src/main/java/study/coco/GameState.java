package study.coco;

import java.util.HashMap;
import java.util.Map;

public class GameState {

  private Map<String, Integer> stateVars = new HashMap<>();
  private Map<String, Item> items = new HashMap<>();

  public int getItemCount(String itemName) {
    return !items.containsKey(itemName) ? 0 : items.get(itemName).getCount();
  }

  public void setItemCount(String itemName, int count) {
    if (count < 0)
      count = 0;
      
    if (!items.containsKey(itemName))
      items.put(itemName, new Item(new ColoredTextElement(itemName), count));
    else
      items.get(itemName).setCount(count);
  }

  public boolean containsItemName(String itemName) {
    return items.containsKey(itemName);
  }

  public int getStateVar(String stateVar) {
    return !stateVars.containsKey(stateVar) ? 0 : stateVars.get(stateVar);
  }

  public void setStateVar(String stateVar, int value) {
    if (!stateVars.containsKey(stateVar))
      stateVars.put(stateVar, value);
    else
      stateVars.replace(stateVar, value);
  }

  public boolean containsStateVarName(String stateVarName) {
    return stateVars.containsKey(stateVarName);
  }
}
