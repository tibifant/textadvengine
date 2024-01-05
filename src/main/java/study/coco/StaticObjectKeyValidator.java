package study.coco;

import javax.naming.NameNotFoundException;
import java.util.Set;

public class StaticObjectKeyValidator {
  private GameState gameState;
  private Set<String> screenNames;

  public StaticObjectKeyValidator(GameState gameState, Set<String> screenNames) {
    this.gameState = gameState;
    this.screenNames = screenNames;
  }

  public void validateItemName(String itemName) throws NameNotFoundException {
    if (!gameState.containsItemName(itemName))
      throw new NameNotFoundException("Item Name not found: " + itemName);
  }

  public void validateScreenName(String screenName) throws NameNotFoundException {
    if (!screenNames.contains(screenName))
      throw new NameNotFoundException("Screen Name not found: " + screenName);
  }

  public void validateStateVarName(String stateVar) throws NameNotFoundException {
    if (!gameState.containsStateVarName(stateVar))
      throw new NameNotFoundException("StateVar Name not found: " + stateVar);
  }
}
