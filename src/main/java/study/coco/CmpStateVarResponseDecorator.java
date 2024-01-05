package study.coco;

import javax.naming.NameNotFoundException;

public class CmpStateVarResponseDecorator implements IResponseDecorator {
  private String stateVar;
  private int cmpValue;

  public CmpStateVarResponseDecorator(String stateVar, int value) {
    this.stateVar = stateVar;
    this.cmpValue = value;
  }

  @Override
  public boolean isValid(GameState gameState) {
    return gameState.getStateVar(stateVar) == cmpValue;
  }

  @Override
  public void onChoose(GameState engine) { }

  @Override
  public void validate(StaticObjectKeyValidator validator) throws NameNotFoundException {
    validator.validateStateVarName(stateVar);
  }


}
