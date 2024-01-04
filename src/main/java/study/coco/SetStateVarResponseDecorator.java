package study.coco;

public class SetStateVarResponseDecorator implements IResponseDecorator {
  private String stateVar;
  private int value;

  public SetStateVarResponseDecorator(String stateVar, int value) {
    this.stateVar = stateVar;
    this.value = value;
  }

  @Override
  public void onChoose(GameState gameState) {
    gameState.setStateVar(stateVar, value);
  }

  @Override
  public boolean isValid(GameState gameState) {
    return true;
  }
}
