package study.coco;

import javax.naming.NameNotFoundException;

public class SetStateVarResponseDecorator implements IResponseDecorator {
  private String stateVar;
  private int value;

  public SetStateVarResponseDecorator(String stateVar, int value) {
    this.stateVar = stateVar;
    this.value = value;
  }

  @Override
  public boolean isValid(GameState gameState) {
    return true;
  }

  @Override
  public void onChoose(GameState gameState) {
    gameState.setStateVar(stateVar, value);
  }

  @Override
  public void validate(StaticObjectKeyValidator validator) throws NameNotFoundException {
    validator.validateStateVarName(stateVar);
  }
}
